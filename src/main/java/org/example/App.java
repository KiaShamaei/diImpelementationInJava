package org.example;

/**
 * This is main part to check container
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Container open!" );
        Container container = new ContainerImp("org.example");
        var dependency = container.resolve(MyDependency.class);
        dependency.doSomething();

    }
}
