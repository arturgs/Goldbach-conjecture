package com.example.goldbach;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Goldbach extends Activity {

	private EditText et;
	private TextView tv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goldbach);
        Button b = (Button)this.findViewById(R.id.button);
        et = (EditText) findViewById(R.id.edit_text);
        tv  = (TextView) findViewById(R.id.text_view);
        
        // Button click
        b.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0) {
        		String numText = et.getText().toString();
        		int num = Integer.parseInt(numText);
        		String rS;
        		long tT = System.currentTimeMillis(); // reading initial time
        		if (num > 72000000)
        			rS = goldRuleL(num);
        		else
        			rS = goldRule(num);
        		tT = System.currentTimeMillis()-tT; // reading final time (milisec.)
        		tv.setText(rS+"\nTiempo = "+tT+" ms"); // print data
            }
        });
        
    }
        
	public static String goldRule(int tam){

		// ERATOSTENES !!
		// list of prime numbers
		int t2 = tam/2;
		boolean[] isP = new boolean[t2];
	        for (int i = 0; i < t2; i++) {
			isP[i] = true;
		}
		isP[0] = false;
		// removing multiples	
		int total = t2;
		for (int i = 1; (2*i+1)*(2*i+1) < tam; i++) {
			if (isP[i]) {
		                for (int j = i; (2*i+1)*(2*j+1) < tam; j++){
					if (isP[(2*i*j)+i+j]){
						isP[(2*i*j)+i+j] = false;
						total--;
					}
				}
			}
		}
		// write down the primes list
		int[] prim = new int[total];
		prim[0] = 2;
		int j = 1;
		for (int i = 0; i < t2; i++){
			if (isP[i]){
				prim[j] = 2*i+1;
				j++;
			}
		}	
		// starting GOLDBACH !!
		// aux vars
		int k;
		int sol = 0;
		int end = total-1;
		// write down first and last instance of the decomposition
		// more vars
		int f1 = 0; // first solution, lowest number
		int f2 = 0; // first solution, largest number
		int l1 = 0; // second solution, lowest number
		int l2 = 0; // second solution, largest number
		boolean first = true; 
		// scan from the begining
		int pi;
		for (int i = 0; prim[i] <= t2; i++){
			k = end;			
			// going down the vector	
			pi = prim[i];		
			while(prim[k]+pi > tam){	
				k--; 
			}
			// stop, we then start from here
			if (k < total-1) end = k+1;
			// if it is a solutio, keep on vars
			if (prim[k]+pi == tam){
				if (!first){
					l1 = pi;
					l2 = prim[k];
				}
				else {
					f1 = pi;
					f2 = prim[k];
					first = false;				
				}
				sol++;
			}
		}
		// :)
		return "First solution :\t"+f1+"\t"+f2+"\nLast solution :\t"+l1+"\t"+l2+"\nNumber of decompositions = "+sol;
	}



	public static String goldRuleL(int tam){

		int t2 = tam/2;
		int lim = 28000000; // split for large numbers in two steps (memory limited in the Xperia S)
		int limN = 2*lim+1;
		boolean[] isP = new boolean[lim];
	        for (int i = 0; i < lim; i++) {
			isP[i] = true;
		}
		isP[0] = false;
	
		for (int i = 1; (2*i+1)*(2*i+1) < limN; i++) {
			if (isP[i]) {
				for (int j1 = i; (2*i+1)*(2*j1+1) < limN; j1++){
					if (isP[(2*i*j1)+i+j1]){
						isP[(2*i*j1)+i+j1] = false;
					}
				}
			}
		}
		int[] prim = new int[5861455]; // this should be optimized for the available memory
		prim[0] = 2;
		int j = 1;
		for (int i = 0; 2*i+1 < limN; i++){
			if (isP[i]){
				prim[j] = 2*i+1;
				j++;
			}
		}	
		for (int i = 0; i < lim; i++) {
			isP[i] = true;
		}
		for (int i = 1; i < j; i++){
			int k = (int)Math.floor(limN/prim[i])+1;
			if (k%2 == 0) k++;
			while(prim[i]*k < tam) {
				isP[((int)((prim[i]*k-1)/2))-lim] = false;
				k += 2;
			}
		}

		for (int i = 1; 2*i+limN < tam; i++){
			if (isP[i]){
				prim[j] = 2*i+limN;
				j++;
			}
		}	
		int k;
		int sol = 0;
		int end = j-1;
		int endP = end;
		int f1 = 0;
		int f2 = 0; 
		int l1 = 0;
		int l2 = 0;
		boolean first = true; 
		int pi;
		for (int i = 0; prim[i] <= t2; i++){
			k = end;			
			pi = prim[i];	
			while(prim[k]+pi > tam){	
				k--; 
			}
			if (k < endP-1) end = k+1;
			if (prim[k]+pi == tam){
				if (!first){
					l1 = pi;
					l2 = prim[k];
				}
				else {
					f1 = pi;
					f2 = prim[k];
					first = false;				
				}
				sol++;
			}
		}
		// :)
		return "First solution :\t"+f1+"\t"+f2+"\nLast solution :\t"+l1+"\t"+l2+"\nNumber of decompositions = "+sol;
	}    
    
      
}
