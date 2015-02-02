package ca.ualberta.cs.expenseclaim.util;

public class ArrayUtil {
	public static int getIndex(String[] arr,String str){
		for(int i=0;i<arr.length;i++){
			if(arr[i].equals(str)){
				return i;
			}
		}
		return -1;
	}
}
