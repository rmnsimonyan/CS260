import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;


public class Green_channel implements PlugInFilter {

public int setup(String args, ImagePlus im) {
	return DOES_ALL;
}

public void run(ImageProcessor ip) {

int M = ip.getWidth();
int N = ip.getHeight();
int n = 256;

int[] Hist = ip.getHistogram();
double[] BenchHist = new double[]{0,    0,    35,    295,    517,    756,    906,    1003,    1088,    1225,    1242,    1242,    1136,    1169,    1008,    907,    833,    748,    685,    609,    649,    620,    617,    591,    559,    536,    486,    465,    508,    473,    457,    468,    432,    475,    441,    422,    405,    397,    409,    422,    426,    426,    444,    424,    441,    452,    468,    472,    480,    473,    473,    435,    411,    395,    401,    413,    401,    447,    416,    427,    492,    489,    486,    520,    514,    540,    577,    531,    526,    496,    444,    485,    465,    475,    492,    520,    578,    541,    648,    667,    690,    728,    698,    724,    644,    696,    772,    751,    812,    836,    834,    815,    901,    902,    878,    786,    722,    687,    710,    694,    699,    704,    680,    688,    669,    706,    663,    828,    949,    1083,    1237,    1276,    1053,    1015,    878,    786,    693,    589,    544,    450,    364,    220,    150,    107,    55,    48,    37,    44,    35,    34,    30,    35,    25,    27,    23,    21,    18,    23,    20,    28,    31,    26,    22,    34,    25,    26,    27,    25,    23,    37,    32,    29,    35,    46,    39,    30,    53,    44,    68,    59,    83,    102,    100,    124,    136,    134,    192,    197,    225,    259,    354,    402,    498,    570,    626,    568,    507,    492,    491,    437,    458,    403,    449,    443,    524,    793,    807,    670,    658,    895,    1157,    1026,    536,    223,    61,    20,    3,    2,    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0};

    double[] Green = new double[Hist.length];
    Green[0] = Hist[0];
    
    for (int i = 1; i < Hist.length; i++){
        Green[i] = Green[i-1]+Hist[i];
        BenchHist[i] = BenchHist[i -1] + BenchHist[i];
    }
    
    
    
    for (int i = 0; i < BenchHist.length; i++){
        Green[i] = Green[i] / Green[n-1];
        BenchHist[i] = BenchHist[i] / BenchHist[n-1];
    }
    
    
    int[] result = matchHistograms(Green, BenchHist);
    
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
