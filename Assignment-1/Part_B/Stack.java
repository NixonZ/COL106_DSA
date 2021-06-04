// You should utilise your implementation of ArrayDeque methods to implement this
public class Stack implements StackInterface {	
	ArrayDeque Deque = new ArrayDeque();
	public void push(Object o){
		this.Deque.insertLast(o);
    	
  	}

	public Object pop() throws EmptyStackException{
		if( this.isEmpty() )
			throw new EmptyStackException("Stack Empty");
		Object o = null;
		try {
			o = this.Deque.removeLast();
//			System.out.println(this.isEmpty());
		} catch (EmptyDequeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public Object top() throws EmptyStackException{
		if( this.isEmpty() )
			throw new EmptyStackException("Stack Empty");
		else
		{
			Object o = null;
			try {
				o = this.Deque.last();
			} catch (EmptyDequeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return o;
		}
		
	}

	public boolean isEmpty(){
		return this.Deque.isEmpty();
	}

    public int size(){
    	return this.Deque.size();
    }
    public String toString()
    {
    	return this.Deque.toString();
    }
//    public String PRINTTHEUNDERLYINGARRAY() {
//    	return this.Deque.PRINTTHEUNDERLYINGARRAY();
//    }
}