/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ncc_problem;

import java.util.Scanner;

/**
 *
 * @author PC-Phong
 */
public class Mario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        String mario = "";
        for (int i = 0; i < N; i++) {
            int a = in.nextInt();
            if (a % 4 == 0) {
                mario = "false";
            } else {
                mario = "true";
            }
            System.out.println(mario);
        }
    }

}
