/********************************
  The licenses for most software are designed to take away your
freedom to share and change it.  By contrast, the GNU General Public
License is intended to guarantee your freedom to share and change free
software--to make sure the software is free for all its users.  This
General Public License applies to most of the Free Software
Foundation's software and to any other program whose authors commit to
using it.  (Some other Free Software Foundation software is covered by
the GNU Lesser General Public License instead.)  You can apply it to
your programs, too.

 ************************************************************/

package ca.ualberta.cs.expenseclaim.util;


// check item in the array
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
