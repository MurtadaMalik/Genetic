import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;


public class Genetic {

  public static void main(String[] args){
    Organism.run();
  }

}

class Organism {
  byte x1;
  byte x2;
  int val;

  public Organism(){
    x1 = (byte) ((int)(Math.random()*256));
    x2 = (byte) ((int)(Math.random()*256));
    val = fun();
  }

  public Organism(int a, int b){
    x1 = (byte) a;
    x2 = (byte) b;
    val = fun();
  }

  public int fun(){
    return (int) (x1+x2);
  }

  public byte getX1(){
    return x1;
  }
  public byte getX2(){
    return x2;
  }
  public int fitness(){
    return val;
  }

  public static Organism combine(Organism a, Organism b){
    byte n1 , n2;

    int r = (int) (Math.random()*2);
    if(r==0){n1 = a.getX1();}
    else{n1 = b.getX1();}

    r = (int) (Math.random()*2);
    if(r==0){n2 = a.getX2();}
    else{n2 = b.getX2();}

    return new Organism(n1,n2);
  }

  public void mutate(){
    int index = (int) (Math.random()*8);
    x1 |= (1 << index);

    index = (int) (Math.random()*8);
    x2 |= (1 << index);
  }

  public static void run(){
    //define arraylist
    ArrayList<Organism> population = new ArrayList<Organism>();

    //starting point, random Organisms
    Organism[] pop = new Organism[20];
    for(Organism it:pop){
      it = new Organism();
      population.add(it);
    }

    //reproduction
    Organism[] children = new Organism[10];
    for(int i = 0; i < 10; i++){
      children[i] = Organism.combine(population.get(2*i), population.get(2*i+1));
      children[i].mutate();
    }

    //add children into population
    for(Organism it : children){
      population.add(it);
    }

    for(Organism it:population){
      it.genotype();
    }
    System.out.println();

    //sort population
  }

  public void genes(){
    System.out.println( String.format("%8s", Integer.toBinaryString(x1 & 0xFF)).replace(' ' , '0') );
    System.out.println( String.format("%8s", Integer.toBinaryString(x2 & 0xFF)).replace(' ' , '0') );
  }

  public void genotype(){
    System.out.println( String.format("%8s", Integer.toBinaryString(x1 & 0xFF)).replace(' ' , '0') + "." + String.format("%8s", Integer.toBinaryString(x2 & 0xFF)).replace(' ' , '0') + " " + val );
  }

}
