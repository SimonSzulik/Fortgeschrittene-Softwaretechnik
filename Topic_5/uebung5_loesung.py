#/**********/#
#/* Simon Szulik     1474315 */#
#/* Fortgeschr. ST  WS 24/25 */#
#/**********/#
import numpy as np
import pandas as pd
import scipy.stats as stats
import matplotlib.pyplot as plt
import os

def read_sample(filename, title='', sampleSize=0):
    script_dir = os.path.dirname(os.path.abspath(__file__))
    dataDirectory = os.path.join(script_dir)
    file_path = os.path.join(dataDirectory, filename)

    print (file_path)

    df = pd.read_csv(file_path,
                     sep=r"\[|\]\s=",
                     engine="python",
                     index_col=False,
                     skipinitialspace=True,
                     names=['Variable', 'Project', 'Ratio'],
                     usecols=['Project', 'Ratio']
                     )
    if sampleSize > 0:
        df = df.sample(sampleSize, random_state=1)
    df.info()
    df.boxplot(column=['Ratio'], grid=False)
    df.hist(column=['Ratio'], grid=False)
    plt.title(title)
    plt.show()
    return df


def calculate_cohens_d(sample1, sample2):
    mean1, mean2 = np.mean(sample1), np.mean(sample2)
    std1, std2 = np.std(sample1, ddof=1), np.std(sample2, ddof=1)
    pooled_std = np.sqrt(((len(sample1) - 1) * std1*2 + (len(sample2) - 1) * std2*2) /
                         (len(sample1) + len(sample2) - 2))
    return (mean1 - mean2) / pooled_std


def determine_sample_size_threshold(python_data, java_data, alpha=0.01):
    max_size = min(len(python_data), len(java_data))
    sample_size = 1000
    while sample_size >= 0 :
        python_sample = python_data.sample(sample_size, random_state=1)
        java_sample = java_data.sample(sample_size, random_state=1)
        _, p_value = stats.mannwhitneyu(python_sample['Ratio'], java_sample['Ratio'], alternative='two-sided')
        if p_value >= alpha:
            print(f"Schranke gefunden: Ab Sample-Größe {sample_size} ist der Test nicht mehr signifikant (p = {p_value:.4f}).")
            return sample_size
        sample_size -= 1
    print("Keine Schranke innerhalb der verfügbaren Daten gefunden.")
    return max_size


def main():
    plt.close('all')
    print('Statistische Berechnungen zu Häufigkeiten (Übung 5)')
    print('\nEinlesen der ersten Stichprobe (Python)')
    pythonSample = read_sample('pythontryratio.boa.output.txt',
                                sampleSize=10000,
                                title="Python")
    print('Mean:' + str(pythonSample['Ratio'].mean()))
    print('Variance:' + str(pythonSample['Ratio'].var()))
    print('\nEinlesen der zweiten Stichprobe (Java)')
    javaSample = read_sample('javatryratio.boa.output.txt',
                              sampleSize=10000,
                              title="Java")
    print('Mean:' + str(javaSample['Ratio'].mean()))
    print('Variance:' + str(javaSample['Ratio'].var()))

    print('\nStatistische Tests')

    # Mann-Whitney-U-Test
    u_statistic, p_value = stats.mannwhitneyu(javaSample['Ratio'], pythonSample['Ratio'], alternative='two-sided')
    print(f"Mann-Whitney-U-Test: U-Statistik = {u_statistic}, p-Wert = {p_value:.6f}")
    if p_value < 0.01:
        print("Der Unterschied der Mittelwerte ist statistisch signifikant (α = 1%).")
    else:
        print("Der Unterschied der Mittelwerte ist nicht statistisch signifikant (α = 1%).")

    # Effektstärke berechnen (Cohen's d)
    cohens_d = calculate_cohens_d(pythonSample['Ratio'], javaSample['Ratio'])
    print(f"Effektstärke (Cohen's d): {cohens_d:.3f}")

    print('\nExperimentelle Bestimmung der Sample-Größen-Schranke:')
    threshold = determine_sample_size_threshold(pythonSample, javaSample, alpha=0.01)
    print(f"Schranke für die Sample-Größe: {threshold}")

    return

if __name__ == '__main__':
    main()
