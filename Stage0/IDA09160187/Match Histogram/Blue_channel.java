import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;


public class Blue_channel implements PlugInFilter {

public int setup(String args, ImagePlus im) {
	return DOES_ALL;
}

public void run(ImageProcessor ip) {

int M = ip.getWidth();
int N = ip.getHeight();
int n = 256;

int[] Hist = ip.getHistogram();
double[] BenchHist = new double[]{0,    10,    74,    384,    737,    1137,    1417,    1540,    1588,    1608,    1378,    1157,    987,    934,    843,    823,    767,    718,    733,    709,    706,    713,    672,    639,    659,    659,    634,    632,    657,    657,    672,    686,    709,    729,    654,    625,    589,    544,    577,    593,    626,    639,    652,    682,    706,    723,    767,    706,    678,    678,    622,    611,    639,    682,    715,    765,    749,    790,    817,    797,    798,    809,    792,    791,    703,    758,    762,    768,    692,    785,    722,    649,    689,    622,    599,    581,    620,    603,    585,    582,    616,    665,    699,    762,    689,    699,    664,    733,    692,    729,    710,    688,    705,    672,    646,    621,    558,    587,    518,    444,    337,    308,    300,    222,    212,    154,    156,    163,    220,    266,    353,    346,    375,    364,    343,    276,    237,    197,    115,    73,    67,    41,    27,    25,    30,    28,    31,    22,    24,    24,    25,    20,    23,    20,    27,    30,    27,    23,    22,    26,    23,    23,    24,    24,    31,    27,    13,    30,    28,    34,    21,    35,    25,    34,    43,    33,    47,    32,    45,    42,    60,    58,    74,    82,    96,    111,    125,    136,    172,    197,    219,    226,    310,    392,    484,    556,    630,    596,    599,    518,    494,    432,    402,    409,    429,    461,    441,    431,    633,    914,    716,    529,    568,    823,    1051,    816,    421,    146,    29,    11,    6,    1,    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0};

    
    double[] Blue = new double[Hist.length];
    Blue[0] = Hist[0];
    
    for (int i = 1; i < Hist.length; i++){
        Blue[i] = Blue[i-1]+Hist[i];
        BenchHist[i] = BenchHist[i -1] + BenchHist[i];
    }
    
    
    
    for (int i = 0; i < BenchHist.length; i++){
        Blue[i] = Blue[i] / Blue[n-1];
        BenchHist[i] = BenchHist[i] / BenchHist[n-1];
    }
    
    
    int[] result = matchHistograms(Blue, BenchHist);
    
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
