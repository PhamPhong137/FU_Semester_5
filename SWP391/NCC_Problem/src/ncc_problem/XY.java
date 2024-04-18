/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ncc_problem;

import java.util.Scanner;

/**
 *
 * @author PC-Phong
 */
public class XY {

    public static void main(String[] args) {
       Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            int second = in.nextInt();
            int pos = in.nextInt();
            char cellType = findCell(second, pos);
            System.out.println(cellType);
        }
               
    }

    public static char findCell(int k, int p) {
     
        if (k == 0) {
            return 'X';
        }

        int half_length = (int) Math.pow(2,k)/2;

        if (p <= half_length) {
       
            return findCell(k - 1, p);
        } else {
           
            char cell = findCell(k - 1, p - half_length);
            return (cell == 'X') ? 'Y' : 'X';
        }
    }
}
