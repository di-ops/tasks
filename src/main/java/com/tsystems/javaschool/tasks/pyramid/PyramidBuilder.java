package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {

        try {
            int size = inputNumbers.size();
            int count = 0;
            while(size>0){
                count++;
                size -=count;
            }
            if (size!=0){
                throw new CannotBuildPyramidException();
            }
            Collections.sort(inputNumbers);
            int countInput = 0;
            int [][] array = new int [count][count*2-1];
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count*2-1; j++) {
                    if ((i%2==0 && (count-1-j)%2==0 && ((j <= count-1 + i)
                                         && (j >= count-1 - i)))// fill odd rows
                        || ((i%2!=0 && (count-1-j)%2!=0 && ((j <= count + i)
                            && (j >= count-2 - i))))// fill even rows

                    ){
                        array[i][j]=inputNumbers.get(countInput);
                        countInput++;
                    }else {
                        array[i][j]=0;
                    }
                }
            }
            return array;
        }
        catch (Exception e){
            throw new CannotBuildPyramidException();
        }
    }


}
