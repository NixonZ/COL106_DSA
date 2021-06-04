import java.io.*; 
import java.util.Scanner; 

public class FabricBreakup {	
	public static void main(String args[]){
		// Implement FabricBreakup puzzle using Stack interface
		FileInputStream file;
		Scanner sc;
//	    StackInterface pile = new Stack();
		try {
			file = new FileInputStream(args[0]);
			sc = new Scanner(file);
			int N = sc.nextInt();
			StackInterface maxStack = new Stack();
			StackInterface CounterStack = new Stack();
			
			for(int i = 0;i < N; i++ ) {
				int sno = sc.nextInt();
				int action = sc.nextInt();

				if(action == 1)
				{
					// move from heap to pile
					int shirt_rank = sc.nextInt();
//					System.out.println("Pushing Shirt whit value:" + Integer.toString(shirt_rank) );
//					pile.push(shirt_rank);
//					System.out.println(pile.toString());
					if(maxStack.isEmpty())
					{
						maxStack.push(shirt_rank);
						CounterStack.push(0);
//						maxIndexStack.push(i);
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
					
					//O(n^2)
//					if(pile.isEmpty())
//						System.out.println( Integer.toString(sno) + " " + Integer.toString(-1) );
//					else
//					{
//						StackInterface throwlist = new Stack();
//						int MAX = 0;
//						int MIN_index_of_MAX = 0;
//						int size_of_pile = pile.size();
//						try {
//							MAX = (int) pile.top();
//							MIN_index_of_MAX = 0;
//						} catch (EmptyStackException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
////						System.out.println("Pile at party: " + pile.toString());
//						int index = 0;
//						while(!pile.isEmpty())
//						{
//							int temp = 0;
//							try {
//								temp = (int) pile.pop();
//							} catch (EmptyStackException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							if(temp > MAX)
//							{
//								MAX = temp;
//								MIN_index_of_MAX = index;
//							}
//							throwlist.push(temp);
//							index++;
//						}
////						System.out.println("Emptied: " + pile.toString());
////						System.out.println("ThrowList: " + throwlist.toString());
//						try {
//							int count = throwlist.size() - 1;
//							while( count != MIN_index_of_MAX  )
//							{
//								pile.push(throwlist.pop());
//								count--;
//							}
//						} catch (EmptyStackException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
////						System.out.println("MAX: "+Integer.toString(MAX) + " MIN_index: " + Integer.toString(MIN_index_of_MAX) );
//						 System.out.println( Integer.toString(sno) + " " + Integer.toString(throwlist.size()-1) );
//						 
////						 System.out.println( Integer.toString(sno) + " " + Integer.toString(throwlist.size()-1) );
////						 System.out.println("Pile Now " + pile.toString());
//					}
					
				}
				
//				System.out.println("Pile After S.no."+ Integer.toString(sno) + pile.PRINTTHEUNDERLYINGARRAY() );
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
