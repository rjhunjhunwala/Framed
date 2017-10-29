/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.util.Comparator;

/**
 *
 * @author rohan
 * 
 */
public class Compare implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
return (int) (10*(((Face) o1).dist()-((Face) o2).dist()));
	}
	
}
