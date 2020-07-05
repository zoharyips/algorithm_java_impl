package com.zohar.arithmetic;

public class Permutations {
    public static void nextPermutation(int[] nums) {
        if (nums.length < 2) return;
        boolean isSwapped = false;
        int lastNum = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            if (lastNum <= nums[i]) {
                lastNum = nums[i];
                for (int j = i; j < nums.length - 1; j++) {
                    nums[j] ^= nums[j + 1];
                    nums[j + 1] ^= nums[j];
                    nums[j] ^= nums[j + 1];
                }
                isSwapped = true;
                continue;
            }
            if (!isSwapped) {
                nums[i] ^= nums[i + 1];
                nums[i + 1] ^= nums[i];
                nums[i] ^= nums[i + 1];
                return;
            } else {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] > nums[i]) {
                        nums[j] ^= nums[i];
                        nums[i] ^= nums[j];
                        nums[j] ^= nums[i];
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        nextPermutation(new int[]{2,2,7,5,4,3,2,2,1});
    }
}
