package tech.alvarez;

/**
 * Created on 12/11/16.
 *
 * @author Daniel Alvarez
 */
public class ProcessSimulator {

    private int np = 0;
    private int[] ini = new int[99];
    private int[] cpu = new int[99];
    private int[] fin = new int[99];
    private int[] T = new int[99];
    private int[] E = new int[99];
    private float[] I = new float[99];

    public ProcessSimulator() {
    }

    public void adicionarProceso(int ti, int tcpu) {
        ini[np] = ti;
        cpu[np] = tcpu;
        ++np;
    }

    public int sumaTiempoCPU() {
        int c = 0;

        for (int i = 0; i < np; ++i) {
            c += cpu[i];
        }

        return c;
    }

    public void fifo() {
        fin[0] = ini[0] + cpu[0];
        int p = fin[0];
        int i = 1;
        int ocio = 0;

        while (i < np) {
            if (ini[i] <= p) {
                fin[i] = cpu[i] + fin[i - 1] + ocio;
                ocio = 0;
                p = fin[i];
                ++i;
            } else {
                ++ocio;
                ++p;
            }
        }

        calcularTEI();
    }

    public void rr() {
        boolean su = false;
        int s = 1;
        int u = 0;
        boolean c = false;
        boolean x = false;
        boolean y = false;
        int b = 0;
        int a = 0;
        int var13 = ini[0];
        int[] cpu2 = new int[np + 1];

        for (int k = 0; k < np; ++k) {
            cpu2[k] = cpu[k];
        }

        while (true) {
            while (s <= np) {
                for (int j = 0; j < np; ++j) {
                    if (cpu2[j] != 0) {
                        ++var13;
                        --cpu2[j];
                        if (cpu2[j] == 0) {
                            fin[j] = var13;
                            ++s;
                            if (ini[j + 1] > var13) {
                                a = j;
                                su = true;
                                u = ini[j + 1];
                            }
                        }

                        if (ini[j + 1] > var13) {
                            int var14 = 0;

                            for (int i = 0; i < a; ++i) {
                                if (cpu2[i] != 0) {
                                    ++var14;
                                    b = i;
                                }
                            }

                            if (var14 == 1) {
                                var13 += cpu2[b];
                                fin[b] = var13;
                                cpu2[b] = 0;
                                x = false;
                                ++s;
                            }
                            break;
                        }
                    } else if (su) {
                        var13 += u - var13;
                        su = false;
                        a = 0;
                        x = false;
                    }
                }
            }

            calcularTEI();
            return;
        }
    }

    public void hrn() {
        int n = np - 1;
        int j = ini[0];
        int tope = j;
        int tope1 = 0;
        int[] ejec = new int[6];
        float[] prio = new float[6];
        int[] tespera = new int[6];
        float men = 90.0F;
        boolean pos = false;

        for (int a = 0; a <= n; ++a) {
            if (a < 1) {
                tope1 = tope + cpu[0];
                fin[0] = tope1;
                ejec[0] = 1;
            }

            if (a > 0) {
                int x;
                for (x = 0; x <= n; ++x) {
                    if (ejec[x] == 1) {
                        tespera[x] = -1;
                    } else {
                        int resta = tope1 - ini[x];
                        tespera[x] = resta;
                    }
                }

                for (x = 0; x <= n; ++x) {
                    if (ejec[x] == 1) {
                        prio[x] = 9999.0F;
                    } else if (tespera[x] >= 0) {
                        prio[x] = (float) (tespera[x] + cpu[x]) / (float) cpu[x];
                    }
                }

                men = 99.0F;
                int var13 = 0;

                for (x = 0; x <= n; ++x) {
                    if (prio[x] != 9999.0F && prio[x] != 0.0F && prio[x] <= men) {
                        men = prio[x];
                        var13 = x;
                    }
                }

                ejec[var13] = 1;
                tope1 += cpu[var13];
                fin[var13] = tope1;
            }
        }

        calcularTEI();
    }

    public void calcularTEI() {
        for (int i = 0; i < np; ++i) {
            T[i] = fin[i] - getIni()[i];
            E[i] = T[i] - cpu[i];
            I[i] = (float) cpu[i] / (float) T[i];
        }

    }

    public void mostrar() {
        System.out.println();

        for (int i = 0; i < np; ++i) {
            System.out.print(getIni()[i] + "  ");
            System.out.print(cpu[i] + "  ");
            System.out.print(fin[i] + "  ");
            System.out.print(T[i] + "  ");
            System.out.print(E[i] + "  ");
            System.out.print(I[i] + "\n");
        }

    }

    public String[] getProceso(int i) {
        return new String[]{String.valueOf((char) (65 + i)), String.valueOf(getIni()[i]), String.valueOf(cpu[i]), String.valueOf(fin[i]), String.valueOf(T[i]), String.valueOf(E[i]), String.valueOf(I[i])};
    }

    public int getNP() {
        return np;
    }

    public String[][] getDatosI() {
        String[][] X = new String[np][7];

        for (int i = 0; i < np; ++i) {
            X[i][0] = String.valueOf((char) (i + 65));
            X[i][1] = String.valueOf(getIni()[i]);
            X[i][2] = String.valueOf(cpu[i]);
            X[i][3] = null;
            X[i][4] = null;
            X[i][5] = null;
            X[i][6] = null;
        }

        return X;
    }

    public String[][] getDatos() {
        String[][] X = new String[np][7];

        for (int i = 0; i < np; ++i) {
            X[i][0] = String.valueOf((char) (i + 65));
            X[i][1] = String.valueOf(getIni()[i]);
            X[i][2] = String.valueOf(cpu[i]);
            X[i][3] = String.valueOf(fin[i]);
            X[i][4] = String.valueOf(T[i]);
            X[i][5] = String.valueOf(E[i]);
            X[i][6] = String.valueOf(I[i]);
        }

        return X;
    }

    public int[][] getDatos2() {
        int[][] X = new int[np][5];

        for (int i = 0; i < np; ++i) {
            X[i][0] = getIni()[i];
            X[i][1] = cpu[i];
            X[i][2] = fin[i];
            X[i][3] = T[i];
            X[i][4] = E[i];
        }

        return X;
    }

    public int maximoY() {
        int max = 0;

        for (int i = 0; i < np; ++i) {
            if (fin[i] > max) {
                max = fin[i];
            }
        }

        return max;
    }

    public int[] getIni() {
        return ini;
    }
}
