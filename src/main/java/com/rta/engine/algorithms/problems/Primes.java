package com.rta.engine.algorithms.problems;

public class Primes {

    public static boolean isPrimeNaive(int n){
        if (n < 2) return false;
        //from 2 to that num , check if any num is divisible by n , if yes then it's not prime
        for (int i =2;i<n;i++){
            if (n % i == 0) return false;
        }
        return true;
    }

    //divisors comes in pairs for eg : (2,18), (3,12), (4,9), (6,6), after this the pairs repeat
    public static boolean isPrimeSqrt(int n){
        if (n < 2) return false;

        //we still check for all nos in between one by one
        for(int i = 2; i*i<=n;i++){
            if (n % i == 0) return false;
        }
        return true;
    }

    //we don't need to check for all nos , skip evens
    public static boolean isPrimeSkipEven(int n){
        if (n <=1) return false;
        if ( n==2) return true;
        if ( n%2==0) return false;
        for(int i=3; i*i<=n;i+=2){
            if (n % i == 0) return false;
        }
        return true;
    }

    // divisor count is only 2 , hence we can check that
    public static boolean isPrimeDivisorCount(int n){
        if (n < 2) return false;

        int count =0;

        for(int i=2; i*i<=n;i++){
            if(n%i==0){
                count++;
                // why this ? because if it's not (6,6) then count increases by one more
                if(i != n/i) {
                    count++;
                }
            }

        }
        return count == 2;
    }
}
