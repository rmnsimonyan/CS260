import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;


public class Red_channel implements PlugInFilter {

public int setup(String args, ImagePlus im) {
	return DOES_ALL;
}

public void run(ImageProcessor ip) {

int M = ip.getWidth();
int N = ip.getHeight();
int n = 256;

int[] Hist = ip.getHistogram();
double[] BenchHist = new double[]{1,    0,    36,    296,    522,    754,    891,    1020,    1132,    1209,    1199,    1176,    1099,    948,    767,    778,    622,    613,    532,    485,    420,    384,    409,    336,    343,    311,    279,    294,    292,    314,    320,    280,    231,    237,    209,    170,    188,    191,    200,    196,    203,    194,    189,    185,    233,    201,    225,    213,    248,    264,    264,    248,    272,    280,    237,    264,    264,    242,    296,    247,    251,    258,    247,    244,    275,    246,    253,    291,    279,    263,    267,    292,    293,    294,    317,    330,    325,    318,    344,    324,    321,    315,    283,    296,    313,    302,    304,    297,    335,    364,    350,    334,    364,    376,    367,    422,    435,    488,    443,    463,    477,    424,    428,    551,    568,    552,    553,    661,    720,    869,    826,    800,    771,    797,    798,    790,    782,    792,    682,    641,    591,    594,    648,    698,    701,    652,    646,    593,    570,    544,    555,    574,    614,    617,    625,    660,    655,    695,    690,    725,    689,    632,    625,    715,    746,    757,    797,    788,    763,    715,    689,    634,    747,    768,    877,    838,    863,    769,    668,    628,    490,    521,    489,    511,    514,    509,    550,    504,    483,    455,    450,    401,    394,    368,    391,    418,    451,    534,    692,    659,    759,    613,    460,    492,    672,    949,    900,    530,    206,    46,    12,    2,    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,    0};

    
double[] Red = new double[Hist.length];
Red[0] = Hist[0];

for (int i = 1; i < Hist.length; i++){
    Red[i] = Red[i-1]+Hist[i];
    BenchHist[i] = BenchHist[i -1] + BenchHist[i];
}


    
for (int i = 0; i < BenchHist.length; i++){
     Red[i] = Red[i] / Red[n-1];
     BenchHist[i] = BenchHist[i] / BenchHist[n-1];
}


int[] result = matchHistograms(Red, BenchHist);

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
