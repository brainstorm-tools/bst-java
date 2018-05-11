package org.brainstorm.connect;

import javax.vecmath.Point3f;

public class BSpline {

    private Point3f[] B;
    private double[] x;
    private int k;
    // Counter used for the a-function values (required length >= degree)
    int[] a = new int[0];
    int[] c = new int[0];

    public BSpline(Point3f[] B, double[] x, int k) {
        this.B = B;
        this.x = x;
        this.k = k;

        if (a.length < k) {
            a = new int[2 * k];
            c = new int[2 * k];
        }
    }

    private double N2(int i, double t) {
        // System.out.println(i);
        int degree = k;
        double[] knot = x;
        double d = 0;

        for (int j = 0; j < degree; j++) {
            double t1 = knot[i + j];
            double t2 = knot[i + j + 1];
            if (t >= t1 && t <= t2 && t1 != t2) {
                int dm2 = degree - 2;

                for (int k = degree - j - 1; k >= 0; k--) {
                    a[k] = 0;
                }

                if (j > 0) {
                    for (int k = 0; k < j; k++) {
                        c[k] = k;
                    }
                    c[j] = Integer.MAX_VALUE;
                } else {
                    c[0] = dm2;
                    c[1] = degree;
                }

                int z = 0;

                while (true) {
                    if (c[z] < c[z + 1] - 1) {
                        double e = 1.0;
                        int bc = 0;
                        int y = dm2 - j;
                        int p = j - 1;

                        for (int m = dm2, n = degree; m >= 0; m--, n--) {
                            if (p >= 0 && c[p] == m) {
                                int w = i + bc;
                                double kd = knot[w + n];
                                e *= (kd - t) / (kd - knot[w + 1]);
                                bc++;
                                p--;
                            } else {
                                int w = i + a[y];
                                double kw = knot[w];
                                e *= (t - kw) / (knot[w + n - 1] - kw);
                                y--;
                            }
                        }

                        // this code updates the a-counter
                        if (j > 0) {
                            int g = 0;
                            boolean reset = false;

                            while (true) {
                                a[g]++;

                                if (a[g] > j) {
                                    g++;
                                    reset = true;
                                } else {
                                    if (reset) {
                                        for (int h = g - 1; h >= 0; h--) {
                                            a[h] = a[g];
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        d += e;

                        // this code updates the bit-counter
                        c[z]++;
                        if (c[z] > dm2) {
                            break;
                        }

                        for (int k = 0; k < z; k++) {
                            c[k] = k;
                        }
                        z = 0;
                    } else {
                        z++;
                    }
                }

                break; // required to prevent spikes
            }
        }

        return d;
    }

    private Point3f P(double t) {
        Point3f sum = new Point3f(0, 0, 0);
        for (int i = 0; i < B.length; i++) {
            sum.set((float) (sum.x + B[i].x * N2(i, t)),
                    (float) (sum.y + B[i].y * N2(i, t)),
                    (float) (sum.z + B[i].z * N2(i, t)));
        }
        return sum;
    }

    public Point3f computeFor(float i) {
        return P(i);
    }
}