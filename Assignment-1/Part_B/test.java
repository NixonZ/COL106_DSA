import java.io.*; 
import java.util.Scanner; 

public class test {	
	public static void main(String args[]){
		// Implement FabricBreakup puzzle using Stack interface
//		FileInputStream file;
		Scanner sc;
	    StackInterface pile = new Stack();

//			file = new FileInputStream(args[0]);
			sc = new Scanner(System.in);
			int N = sc.nextInt();
			StackInterface maxStack = new Stack();
			StackInterface CounterStack = new Stack();
			for(int i = 0;i < N; i++ ) {
				int sno = sc.nextInt();
				int action = sc.nextInt();

				if(action == 1)
				{
					// System.out.println("Nalin ");
					int shirt_rank = sc.nextInt();
					if(maxStack.isEmpty())
					{
						maxStack.push(shirt_rank);
						CounterStack.push(0);
					}
					else
					{
						int maxTop = -1;
						try {
							maxTop = (int) maxStack.top();
						} catch (EmptyStackException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if( maxTop <= shirt_rank)
						{
							maxStack.push(shirt_rank);
							CounterStack.push(0);
						}
						else
						{
							int count = -1;
							try {
								count = (int)CounterStack.pop();
							} catch (EmptyStackException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							CounterStack.push(count+1);
						}
					}
					
				}
				
				if(action == 2)
				{
					// O(n) implementation
					if(maxStack.isEmpty() && CounterStack.isEmpty())
					 {
						 System.out.println(Integer.toString(sno) + " " + Integer.toString(-1) );
						 
					 }
					 else
					 {
						 try {
							maxStack.pop();
						} catch (EmptyStackException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 int count = -1;
						try {
							count = (int) CounterStack.pop();
						} catch (EmptyStackException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 System.out.println(Integer.toString(sno) + " " + Integer.toString(count) );
					 }
				}
//				System.out.println("Pile After S.no."+ Integer.toString(sno) + pile.PRINTTHEUNDERLYINGARRAY() );
			}

	}
}

