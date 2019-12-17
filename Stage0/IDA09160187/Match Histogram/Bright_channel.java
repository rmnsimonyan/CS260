import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;

public class Bright_channel implements PlugInFilter {

public int setup(String args, ImagePlus im) {
	return DOES_ALL;
}

public void run(ImageProcessor ip) {

int M = ip.getWidth();
int N = ip.getHeight();
int n = 256;

int[] Hist = ip.getHistogram();
double[] BenchHist = new double[]{0,    0,    33,    288,    506,    735,    871,    981,    1072,    1177,    1170,    1136,    1051,    972,    815,    807,    668,    631,    540,    468,    412,    385,    405,    321,    340,    319,    275,    303,    289,    329,    307,    281,    232,    243,    199,    174,    197,    187,    208,    191,    196,    205,    195,    181,    222,    206,    218,    204,    257,    268,    256,    242,    271,    283,    235,    259,    258,    236,    296,    234,    251,    261,    250,    235,    276,    240,    249,    289,    275,    260,    267,    307,    298,    290,    324,    316,    333,    326,    342,    336,    324,    310,    279,    303,    305,    305,    304,    301,    339,    368,    340,    344,    367,    374,    362,    409,    422,    494,    442,    456,    494,    427,    434,    548,    573,    534,    519,    565,    589,    665,    750,    788,    760,    815,    824,    806,    801,    857,    753,    739,    709,    660,    679,    705,    711,    666,    640,    588,    576,    550,    548,    580,    604,    616,    626,    667,    652,    687,    673,    720,    682,    633,    615,    712,    750,    756,    773,    781,    750,    695,    667,    619,    715,    720,    812,    758,    759,    644,    506,    462,    325,    268,    212,    148,    131,    119,    137,    133,    183,    201,    217,    232,    312,    392,    486,    552,    616,    594,    601,    514,    499,    422,    401,    406,    437,    449,    435,    437,    649,    926,    724,    531,    577,    828,    1054,    816,    421,    146,    29,    11,    6,    1,    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0};

    
double[] Bright = new double[Hist.length];
Bright[0] = Hist[0];
    
for (int i = 1; i < Hist.length; i++){
        Bright[i] = Bright[i-1]+Hist[i];
        BenchHist[i] = BenchHist[i -1] + BenchHist[i];
    }
    
    
    
for (int i = 0; i < BenchHist.length; i++){
        Bright[i] = Bright[i] / Bright[n-1];
        BenchHist[i] = BenchHist[i] / BenchHist[n-1];
    }
    
    
int[] result = matchHistograms(Bright, BenchHist);
    
for (int v = 0; v < N; v++) {
    for (int u = 0; u < M; u++) {
            int a = ip.get(u, v);
            int b = result[a];
            ip.set(u, v, b);
        }
    }
    
}
    
public int[] matchHistograms (double[] hA, double[] hR) {
        
    int K = hA.length;
    int[] fhs = new int[K]; // mapping fhs()
        
        // compute mapping function fhs():
    for (int a = 0; a < K; a++) {
        int j = K - 1;
        do {
                fhs[a] = j;
                j--;
            } while (j >= 0 && hA[a] <= hR[j]);
        }
        return fhs;
    }
    
}
