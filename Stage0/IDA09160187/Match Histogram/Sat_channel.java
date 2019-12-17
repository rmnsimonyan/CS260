import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;

public class Sat_channel implements PlugInFilter {

public int setup(String args, ImagePlus im) {
	return DOES_ALL;
}

public void run(ImageProcessor ip) {

int M = ip.getWidth();
int N = ip.getHeight();
int n = 256;

int[] Hist = ip.getHistogram();
double[] BenchHist = new double[]{4895,    0,    22,    42,    1058,    92,    1423,    222,    474,    429,    2458,    3173,    1731,    2147,    2963,    1607,    1096,    742,    358,    237,    228,    219,    185,    204,    195,    234,    266,    90,    273,    119,    339,    392,    63,    121,    489,    73,    514,    63,    106,    628,    144,    52,    838,    88,    37,    190,    867,    183,    39,    92,    22,    1005,    55,    98,    118,    94,    807,    122,    146,    46,    123,    73,    42,    873,    17,    52,    91,    129,    77,    137,    202,    76,    519,    56,    92,    253,    166,    120,    128,    273,    157,    204,    202,    262,    128,    1015,    365,    381,    377,    410,    465,    724,    449,    591,    529,    687,    702,    779,    1086,    802,    1026,    672,    1224,    880,    818,    1104,    1067,    883,    822,    935,    820,    819,    843,    784,    808,    861,    705,    692,    671,    880,    969,    937,    891,    934,    839,    917,    800,    1360,    888,    839,    810,    781,    735,    726,    656,    713,    732,    607,    578,    604,    564,    602,    539,    528,    476,    509,    540,    546,    460,    393,    520,    417,    247,    450,    319,    281,    312,    245,    237,    209,    233,    243,    168,    232,    128,    157,    148,    146,    130,    47,    201,    104,    92,    65,    87,    58,    52,    46,    38,    35,    34,    22,    42,    21,    19,    15,    6,    27,    15,    10,    8,    16,    8,    5,    8,    9,    7,    5,    4,    5,    10,    6,    3,    1,    4,    3,    3,    2,    3,    0,    0,    2,    1,    1,    0,    2,    3,    1,    1,    0,    1,    1,    0,    0,    0,    0,    0,    1,    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    1};

    
    
    double[] Sat = new double[Hist.length];
    Sat[0] = Hist[0];
    
    for (int i = 1; i < Hist.length; i++){
        Sat[i] = Sat[i-1]+Hist[i];
        BenchHist[i] = BenchHist[i -1] + BenchHist[i];
    }
    
    
    
    for (int i = 0; i < BenchHist.length; i++){
        Sat[i] = Sat[i] / Sat[n-1];
        BenchHist[i] = BenchHist[i] / BenchHist[n-1];
    }
    
    
    int[] result = matchHistograms(Sat, BenchHist);
    
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
