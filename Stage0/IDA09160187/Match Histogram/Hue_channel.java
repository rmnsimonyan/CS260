import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;

public class Hue_channel implements PlugInFilter {

public int setup(String args, ImagePlus im) {
	return DOES_ALL;
}

public void run(ImageProcessor ip) {

int M = ip.getWidth();
int N = ip.getHeight();
int n = 256;

int[] Hist = ip.getHistogram();
double[] BenchHist = new double[]{5325,    216,    342,    371,    418,    651,    508,    1768,    2123,    3395,    4536,    3353,    4205,    4013,    4734,    4624,    6452,    3384,    4895,    1654,    1117,    687,    276,    286,    70,    28,    190,    6,    1250,    48,    2,    16,    0,    0,    802,    190,    1,    0,    0,    0,    0,    0,    6700,    0,    0,    0,    0,    8,    0,    77,    1,    1016,    0,    0,    0,    0,    3,    0,    0,    226,    19,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1563,    0,    3,    0,    0,    0,    0,    50,    0,    1,    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,    0,    0,    258,    0,    0,    0,    0,    0,    33,    0,    0,    0,    9,    6,    0,    1052,    2,    0,    0,    0,    0,    12,    105,    0,    0,    216,    0,    0,    8,    20,    0,    280,    107,    3,    0,    0,    373,    0,    1,    20,    688,    0,    0,    773,    4040,    0,    0,    61,    2137,    18,    7,    127,    2786,    18,    482,    3356,    157,    61,    2,    270,    8,    47,    21,    736,    280,    55,    147,    59,    63,    1,    202,    159,    31,    44,    0,    3,    10,    0,    0,    491,    0,    13,    12,    1,    34,    8,    75,    100,    1,    1,    13,    6,    0,    18,    0,    1,    75,    28,    0,    1,    3,    2,    0,    0,    10,    1,    0,    166,    1,    26,    0,    0,    1,    0,    23,    1,    3,    1,    0,    0,    0,    8,    0,    0,    0,    3,    0,    0,    0,    0,    48,    1,    0,    0,    0,    15,    0,    0,    0,    0,    6,    1,    548,    0,    0,    0,    0,    0,    10,    46,    1,    0,    0,    156,    0,    11,    13,    17,    19,    140,    60,    136,    144,    141,    0};

    
    double[] Hue = new double[Hist.length];
    Hue[0] = Hist[0];
    
    for (int i = 1; i < Hist.length; i++){
        Hue[i] = Hue[i-1]+Hist[i];
        BenchHist[i] = BenchHist[i -1] + BenchHist[i];
    }
    
    
    
    for (int i = 0; i < BenchHist.length; i++){
        Hue[i] = Hue[i] / Hue[n-1];
        BenchHist[i] = BenchHist[i] / BenchHist[n-1];
    }
    
    
    int[] result = matchHistograms(Hue, BenchHist);
    
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
