public class Dog
{
public int weightInPounds;
public static Dog maxDog(Dog d1, Dog d2) {
    if (weightInPounds > d2.weightInPounds) {
        return this;
    }
    return d2;
}
}

public class DogRunner
{
public static void main(String[] args)
{
Dog a = new Dog(10);
Dog b = new Dog(5);

a.weightInPounds(a,b);
}
}