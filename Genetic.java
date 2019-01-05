import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Genetic {

  public static void main(String[] args){
    Organism.run(200,15,2,"<"); // ( Generations , Population Size / 2 , Mutation Rate , Optimization Target )
  }

}

class Organism {
  double x1;
  double x2;
  double val;
  static double goal;

  public Organism(){
    x1 = Math.random()*500;
    x2 = Math.random()*500;
    val = fitness();
  }

  public Organism(double a, double b){
    x1 = a;
    x2 = b;
    val = fitness();
  }


  //function to be tested
  public double fun(){
    return (x1 + 2*x2 - 7)*(x1 + 2*x2 - 7) + (2*x1 + x2 - 5)*(2*x1 + x2 - 5);
  }

  public double fitness(){
    return val = fun();
  }

  public double getX1(){
    return x1;
  }
  public double getX2(){
    return x2;
  }

  public static Organism combine(Organism a, Organism b){
    double n1 , n2;

    int r = (int) (Math.random()*2);
    if(r==0){n1 = a.getX1();}
    else{n1 = b.getX1();}

    r = (int) (Math.random()*2);
    if(r==0){n2 = a.getX2();}
    else{n2 = b.getX2();}

    return new Organism(n1,n2);
  }

  public void mutate(double maxmut){
    double intercept = (Math.random()*2*maxmut)-maxmut;
    x1 += intercept;

    intercept = (Math.random()*2*maxmut)-maxmut;
    x2 += intercept;
  }

  public static boolean isNumber(String s){
    try {
      double d = Double.parseDouble(s);
    } catch(NumberFormatException nfe) {  return false;  }

    return true;
  }

  public void genes(){
    System.out.println( x1 );
    System.out.println( x2 );
  }

  public void genotype(){
    System.out.println( "(" + String.format("%.4f" , x1) + ").(" + String.format("%.4f" , x2) + ") fit=" + String.format("%.4f" , fitness()) );
  }


  public static void run(int generations, int popNum, double mutVar, String target){ //mutation in range [-mutvar,+mutvar]

    //define arraylist
    ArrayList<Organism> population = new ArrayList<Organism>();

    //starting point, random Organisms
    Organism ancestor;
    for(int i =0; i < 2*popNum ; i++){
      ancestor = new Organism();
      population.add(ancestor);
    }

    for(int gen=0; gen<generations; gen++){

      //evaluation
      double mean=0;
      for(Organism it : population){
        mean += it.fitness();
      }
      mean = mean / (2.0*popNum);
      System.out.println("Generation" + gen + " evaluation: " + mean);

      //shuffle the population - random reproduction
      //Collections.shuffle(population);

      //reproduction
      Organism child;
      for(int i = 0; i < popNum; i++){
        child = Organism.combine(population.get(2*i), population.get(2*i+1));
        child.mutate(mutVar);
        population.add(child);
      }

      if(isNumber(target)){
        goal = Double.parseDouble(target);
        Collections.sort(population, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2)
            {
                return Double.compare( Math.abs(o2.fitness()-goal) , Math.abs(o1.fitness()-goal) );            }
        }
        );
      }
      else{
        //sort population
        Collections.sort(population, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2)
            {
              if(target=="<")
                return Double.compare(o2.fitness() , o1.fitness());
              else
                return Double.compare(o1.fitness() , o2.fitness());
            }
        }
        );
      }

      //remove weak Organisms
      for(int i = 0; i < popNum; i++){
        population.remove(0);
      }

      for(Organism it:population){
        it.genotype();
      }
      System.out.println();
    }
  } //end of run

}
