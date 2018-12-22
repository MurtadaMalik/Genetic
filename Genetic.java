import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;


public class Genetic {

  public static void main(String[] args){
    Organism.run(500,"+",5);
  }
}

class Organism {
  int x1;
  int x2;
  int val;

  public Organism(){
    x1 = (int)(Math.random()*256);
    x2 = (int)(Math.random()*256);
    val = fun();
  }

  public Organism(int a, int b){
    x1 = a;
    x2 = b;
    val = fun();
  }

  //function to be tested
  public int fun(){
    return (int) (-x1*x1-x2*x2+10*x1-2*x2-21);
  }

  public int getX1(){
    return x1;
  }
  public int getX2(){
    return x2;
  }
  public int fitness(){
    val = fun();
    return val;
  }

  public static Organism combine(Organism a, Organism b){
    int n1 , n2;

    int r = (int) (Math.random()*2);
    if(r==0){n1 = a.getX1();}
    else{n1 = b.getX1();}

    r = (int) (Math.random()*2);
    if(r==0){n2 = a.getX2();}
    else{n2 = b.getX2();}

    return new Organism(n1,n2);
  }

  public void mutate(int maxmut){
    int intercept = (int)(Math.random()*2*maxmut)-maxmut;
    x1 += intercept;

    intercept = (int) (Math.random()*2*maxmut)-maxmut;
    x2 += intercept;
  }

  public static void run(int generations, String target, int mutvar){
    //define arraylist
    ArrayList<Organism> population = new ArrayList<Organism>();

    //starting point, random Organisms
    Organism[] pop = new Organism[20];
    for(Organism it:pop){
      it = new Organism();
      population.add(it);
    }

    double mean=0;
    for(int gen=0; gen<generations; gen++){

      //evaluation
      for(Organism it : population){
        mean += it.fitness();
      }
      mean = mean / 20.0;
      System.out.println("Generation" + gen + " evaluation: " + mean);

      //reproduction
      Organism[] children = new Organism[10];
      for(int i = 0; i < 10; i++){
        children[i] = Organism.combine(population.get(2*i), population.get(2*i+1));
        children[i].mutate(mutvar);
      }

      //add children into population
      for(Organism it : children){
        population.add(it);
      }

      //sort population
      bubble(target,population);

      //remove weak Organisms
      for(int i = 0; i < 10; i++){
        population.remove(0);
      }

      for(Organism it:population){
        it.genotype();
      }
      System.out.println();
    }
  }

  public void genes(){
    System.out.println( String.format("%8s", Integer.toBinaryString(x1 & 0xFF)).replace(' ' , '0') );
    System.out.println( String.format("%8s", Integer.toBinaryString(x2 & 0xFF)).replace(' ' , '0') );
  }

  public void genotype(){
    System.out.println( String.format("%8s", Integer.toBinaryString(x1 & 0xFF)).replace(' ' , '0') + "." + String.format("%8s", Integer.toBinaryString(x2 & 0xFF)).replace(' ' , '0') + " " + fitness() );
  }

  public static void bubble(String s , ArrayList<Organism> population){
    Organism temp;
    boolean swapped;

    //ascending order
    if(s=="+"){

      for (int i = 0; i < population.size() - 1; i++)
      {
          swapped = false;
          for (int j = 0; j < population.size() - i - 1; j++)
          {
            if (population.get(j).fitness() > population.get(j+1).fitness())
            {
                // swap Organism[j] and Organism[j+1]
                temp = population.get(j);
                population.set(j,population.get(j+1));
                population.set(j+1,temp);
                swapped = true;
            }
          }
          if (swapped == false)
              break;
      }

    }
    //descending order
    else if(s=="-"){

      for (int i = 0; i < population.size() - 1; i++)
      {
          swapped = false;
          for (int j = 0; j < population.size() - i - 1; j++)
          {
            if (population.get(j).fitness() < population.get(j+1).fitness())
            {
                // swap Organism[j] and Organism[j+1]
                temp = population.get(j);
                population.set(j,population.get(j+1));
                population.set(j+1,temp);
                swapped = true;
            }
          }
          if (swapped == false)
              break;
      } //end of for loop

    }
    else{
      System.out.println("Target not applicable");
    }
  } //end of bubble

}
