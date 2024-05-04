import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class EDASmain {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        EdasSPK spk = new EdasSPK();
        ArrayList<Kriteria> kriteria = new ArrayList<>();
        // true = benefit , false = cost
        kriteria.add(new Kriteria("Luas Tanah", 0.2904f, true));
        kriteria.add(new Kriteria("Harga", 0.1708f, false));
        kriteria.add(new Kriteria("Tipe", 0.2133f, true));
        kriteria.add(new Kriteria("Sumber Air", 0.0441f, true));
        kriteria.add(new Kriteria("Kamar Tidur", 0.1399f, true));
        kriteria.add(new Kriteria("Kamar Mandi", 0.0293f, true));
        kriteria.add(new Kriteria("Pos Satpam", 0.0498f, true));
        kriteria.add(new Kriteria("Lokasi", 0.0624f, false));

        ArrayList<Alternatif> alternatif = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            alternatif.add(new Alternatif(("A" + (i+1))));
        }
        // matriks keputusan dari jobsheet
        int[][] matriksAwal = {
                { 7, 7, 9, 7, 1, 9, 1, 6 },
                { 4, 3, 5, 3, 3, 10, 7, 4 },
                { 5, 5, 7, 4, 9, 2, 6, 1 },
                { 9, 5, 10, 9, 7, 8, 6, 7 },
                { 10, 1, 9, 2, 10, 6, 10, 2 },
                { 6, 7, 2, 7, 10, 2, 8, 7 },
                { 10, 9, 10, 2, 6, 7, 10, 4 },
                { 7, 6, 6, 4, 7, 2, 3, 9 },
                { 4, 9, 9, 1, 6, 1, 6, 1 },
                { 9, 8, 2, 4, 5, 3, 2, 5 },
                { 4, 4, 2, 5, 4, 10, 6, 5 },
                { 10, 6, 9, 9, 10, 10, 2, 6 },
                { 6, 8, 8, 5, 4, 9, 2, 3 },
                { 8, 6, 3, 3, 6, 7, 8, 7 },
                { 4, 9, 3, 5, 10, 5, 10, 6 },
                { 6, 2, 7, 6, 5, 6, 5, 9 },
                { 8, 4, 1, 8, 3, 10, 7, 4 },
                { 4, 1, 8, 5, 10, 5, 2, 6 },
                { 9, 8, 9, 5, 1, 3, 10, 6 },
                { 3, 1, 10, 4, 7, 5, 10, 8 }
        };

        int pilmenu;
    do {
        System.out.println("     EDAS: PEMILIIHAN RUMAH");
        System.out.println("===============================");
        System.out.println("Matriks keputusan :");
        System.out.println("\n____________________________________________");
        System.out.print("      |");
        for (int i = 0; i < kriteria.size(); i++) {
            System.out.print(" C" + (i+1) + " ");
        }
        System.out.println("\n______|_____________________________________");
        System.out.printf("%4s  |\n", " ");
        for (int i = 0; i < matriksAwal.length; i++) {
            System.out.printf("%4s  |",alternatif.get(i).getNamaAlternatif());
            for (int j = 0; j < matriksAwal[0].length; j++) {
                System.out.printf(" %2d " ,matriksAwal[i][j]);
            }
            System.out.println();
        }
        pilmenu = menu();
        switch (pilmenu) {
            case 1:
                double[] AV = spk.rataAv(matriksAwal);

                //menghitung jarak positif
                double[][] pda = new double[alternatif.size()][kriteria.size()];
                for (int i = 0; i < pda.length; i++) {
                    for (int j = 0; j < pda[0].length; j++) {
                        pda[i][j] = spk.PDA(matriksAwal[i][j], AV[j], kriteria.get(j).getJenis());
                    }
                }

                double[] SP = spk.SPSN(pda, kriteria);

                double[] NSP = new double[alternatif.size()];
                for (int i = 0; i < NSP.length; i++) {
                    NSP[i] =  SP[i] / Arrays.stream(SP).max().orElse(0);
                }
                
                
                //menghitung jarak negatif
                double[][] nda = new double[alternatif.size()][kriteria.size()];
                for (int i = 0; i < pda.length; i++) {
                    for (int j = 0; j < pda[0].length; j++) {
                        nda[i][j] = spk.NDA(matriksAwal[i][j], AV[j], kriteria.get(j).getJenis());
                    }
                }
                double[] SN = spk.SPSN(nda, kriteria);

                double[] NSN = new double[alternatif.size()];
                for (int i = 0; i < NSN.length; i++) {
                    NSN[i] =  1 - (SN[i] / Arrays.stream(SN).max().orElse(0));
                }

                //menghitung nilai skor penilaian (AS)
                Double[] AS = new Double[alternatif.size()];
                for (int i = 0; i < AS.length; i++) {
                    AS[i] = (NSP[i] + NSN[i]) / 2;
                }

                System.out.println("----------SKOR PENILAIAN----------");
                for (int i = 0; i < AS.length; i++) {
                    System.out.println(alternatif.get(i).getNamaAlternatif() + " = " + AS[i]);
                }
                Double max = Collections.max(Arrays.asList(AS));
                int rank1 = Arrays.asList(AS).indexOf(max);

                System.out.println("\nDapat disimpulkan, " + alternatif.get(rank1).getNamaAlternatif() + " merupakan alternatif terbaik dengan skor = " + AS[rank1]);

                break;

            case 2:
            System.out.print("\nBaris ke- : ");
            int bar = sc.nextInt();
            System.out.print("Kolom ke- : ");
            int kol = sc.nextInt();
            System.out.print("Nilai baru : ");
            int nil = sc.nextInt();

            matriksAwal[bar-1][kol-1] = nil;
            
            System.out.println("==PERUBAHAN TERSIMPAN==\n");
                break;

            case 3:
            System.out.println("----------EDIT KRITERIA----------");
            for (int i = 0; i < kriteria.size(); i++) {
                System.out.printf("%2d. %13s  %5s  %7s\n", (i+1), kriteria.get(i).getNamaKriteria(), kriteria.get(i).getBobot(), (kriteria.get(i).getJenis() ? "Benefit" : "Cost"));
            }
            System.out.print("Pilih kriteria yg akan diedit (masukan angka) : ");
            int krit = sc.nextInt();
            System.out.print("Nama Kriteria : ");
            String nama = sc.next();
            System.out.print("Bobot Kriteria : ");
            float bobot = sc.nextFloat();
            System.out.print("Jenis Kriteria (Benefit/Cost) : ");
            String jen = sc.next();

            kriteria.get(krit-1).setNamaKriteria(nama);
            kriteria.get(krit-1).setBobot(bobot);
            kriteria.get(krit-1).setJenis(jen.equalsIgnoreCase("benefit") ? true : false);
            System.out.println("==PERUBAHAN TERSIMPAN==\n");
                break;

            case 4:
                System.out.print("Nama Kriteria : ");
                String addC = sc.next();
                System.out.print("Bobot Kriteria : ");
                float addCw = sc.nextFloat();
                System.out.print("Jenis Kriteria (benefit/cost)");
                String addCj = sc.next();
                kriteria.add(new Kriteria(addC, addCw, (addCj.equalsIgnoreCase("benefit") ? true : false)));

                System.out.println("Masukkan nilai untuk tiap alternatif");
                int[] baruC= new int[alternatif.size()];
                for (int i = 0; i < alternatif.size(); i++) {
                    System.out.print(alternatif.get(i).getNamaAlternatif() + "(1-10) : ");
                    int nilai = sc.nextInt();
                    baruC[i] = nilai;
                }
                matriksAwal = spk.tambahKriteria(matriksAwal, baruC);
                break;

            case 5:
            System.out.print("Nama Alternatif : ");
            String addA = sc.next();
            alternatif.add(new Alternatif(addA));
            System.out.println("Masukkan nilai untuk tiap kriteria");

            int[] baruA = new int[kriteria.size()];
            for (int i = 0; i < baruA.length; i++) {
                System.out.print(kriteria.get(i).getNamaKriteria()+" (1-10) : ");
                baruA[i] = sc.nextInt();
            }
            matriksAwal = spk.tambahAlternatif(matriksAwal, baruA);
                break;

            case 0:
            System.out.println("===TERIMAKASIH===");
            break;

            default:
            System.out.println("MASUKAN SALAH!");
                break;
        }
    } while (pilmenu!=0);
        

    }

    public static int menu() {
        System.out.println("================================");
        System.out.println(" 1. Hitung");
        System.out.println(" 2. Edit Matriks Keputusan");
        System.out.println(" 3. Edit Kriteria");
        System.out.println(" 4. Tambah Kriteria");
        System.out.println(" 5. Tambah Alternatif");
        System.out.println(" 0. Keluar");
        System.out.println("================================");
        System.out.print("Pilih (0-5) = ");
        int pilih = sc.nextInt();

        return pilih;
    }
}
