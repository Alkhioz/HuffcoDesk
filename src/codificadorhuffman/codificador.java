/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codificadorhuffman;

import java.util.ArrayList;
import java.util.PriorityQueue;

 
/**
 *
 * @author Usuario
 */   

    abstract class ARBOL_HUFFMAN implements Comparable<ARBOL_HUFFMAN> {
    public final int FRECUENCIA; // the frequency of this tree   
    public ARBOL_HUFFMAN(int freq) { FRECUENCIA = freq; }
 
    // compares on the frequency
    public int compareTo(ARBOL_HUFFMAN ARBOL) {
        return FRECUENCIA - ARBOL.FRECUENCIA;
    }
}

class HOJA_HUFFMAN extends ARBOL_HUFFMAN {
    public final char VALOR; // el caracter que posse la hoja
 
    public HOJA_HUFFMAN(int freq, char val) {
        super(freq);
        VALOR = val;
    }
}
 

class NODO_HUFFMAN extends ARBOL_HUFFMAN {
    public final ARBOL_HUFFMAN IZQUIERDA, DERECHA; // subtrees
 
    public NODO_HUFFMAN(ARBOL_HUFFMAN l, ARBOL_HUFFMAN r) {
        super(l.FRECUENCIA + r.FRECUENCIA);
        IZQUIERDA = l;
        DERECHA = r;
    }
}
 
public class codificador {
    
    public static ArrayList<String> simbolos = new ArrayList<String>();
     public static ArrayList<String> frecuencia = new ArrayList<String>();
      public static ArrayList<String> codigo = new ArrayList<String>();
      
    // input is an array of frequencies, indexed by character code
    public static ARBOL_HUFFMAN buildTree(int[] charFreqs) {
        PriorityQueue<ARBOL_HUFFMAN> ARBOLES = new PriorityQueue<ARBOL_HUFFMAN>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                ARBOLES.offer(new HOJA_HUFFMAN(charFreqs[i], (char)i));
 
        assert ARBOLES.size() > 0;
        // loop until there is only one tree left
        while (ARBOLES.size() > 1) {
            // two trees with least frequency
            ARBOL_HUFFMAN a = ARBOLES.poll();
            ARBOL_HUFFMAN b = ARBOLES.poll();
 
            // put into new node and re-insert into queue
            ARBOLES.offer(new NODO_HUFFMAN(a, b));
        }
        return ARBOLES.poll();
    }
 
    public static void IMP_CODIGOS(ARBOL_HUFFMAN tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HOJA_HUFFMAN) {
            HOJA_HUFFMAN leaf = (HOJA_HUFFMAN)tree;
 
            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leaf.VALOR + "\t" + leaf.FRECUENCIA + "\t" + prefix);
            simbolos.add(""+leaf.VALOR+"");
            frecuencia.add(""+leaf.FRECUENCIA);
            codigo.add(""+prefix);
 
        } else if (tree instanceof NODO_HUFFMAN) {
            NODO_HUFFMAN node = (NODO_HUFFMAN)tree;
 
            // traverse left
            prefix.append('0');
            IMP_CODIGOS(node.IZQUIERDA, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            IMP_CODIGOS(node.DERECHA, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
 
}
