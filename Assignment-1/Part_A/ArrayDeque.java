public class ArrayDeque implements DequeInterface {
	private Object[] Array;
	private int front = 0;
	private int rear = 0;
	private int N = 0;
	private int array_size = 0;
	
	public ArrayDeque() {
		this.Array = new Object[1];
		this.N = 1;
		this.front = 0;
		this.rear = 0;
		this.array_size = 0;
	}
	
private void doubleArray()
{
//	System.out.println("Double time");
	Object[] temp = new Object[this.N*2];
	for(int i = 0 ;i < this.array_size ; i++)
	{
		temp[i] = Array[(front+i)%N];
	}
	this.Array = temp;
	this.N = 2*this.N;
	this.front = 0;
	this.rear = this.array_size-1;
//	System.out.println(this.toString());
}

  public void insertFirst(Object o){
	  if(this.isEmpty())
	  {
		  rear = front = 0;
		  Array[front] = o;
	  }
	  else
	  {
		  front = (front-1+N)%N;
//		  System.out.println("Inserting First at " + Integer.toString(front%N) );
		  Array[front%N] = o;
		  
	  }
	  this.array_size = this.array_size + 1;
//	  System.out.println(this.toString());
	  if( this.array_size == N )
      {
      	this.doubleArray();
      }
  }
  
  public void insertLast(Object o){ 
	  if(this.isEmpty())
	  {
		  rear = front = 0;
		  Array[rear] = o;
	  }
	  else
	  {
		  rear = (rear+1)%N;
	      Array[rear] = o;
	  }
      this.array_size = this.array_size + 1;
//      System.out.println("Inserting Last");
//      System.out.println(this.toString());
      if( this.array_size == N )
      {
      	this.doubleArray();
      }
  }
  
  public Object removeFirst() throws EmptyDequeException{
	  if( this.isEmpty() )
		  throw new EmptyDequeException("Array Empty");
	  else
	  {
//		  System.out.println("Removing First");
		  this.array_size = this.array_size - 1;
		  Object temp = Array[front];
		  front = (front + 1+N)%N;
		  if(this.isEmpty())
			  rear=front=0;
		  return temp;
	  }
  }
  
  public Object removeLast() throws EmptyDequeException{
	  if( this.isEmpty() )
		  throw new EmptyDequeException("Array Empty");
	  else
	  {
//		  System.out.println("Removing Last");
		  this.array_size = this.array_size - 1;
		  Object temp = Array[rear];
		  rear = (rear - 1+N)%N;
//		  System.out.println(this.toString());
//		  System.out.println(this.array_size);
		  if(this.isEmpty())
			  rear=front=0;
		  return temp;
	  }
  }

  public Object first() throws EmptyDequeException{
	  if( this.isEmpty() )
		  throw new EmptyDequeException("Array Empty");
	  else
	  {
		  return Array[front];
	  }
  }
  
  public Object last() throws EmptyDequeException{
	  if( this.isEmpty() )
		  throw new EmptyDequeException("Array Empty");
	  else
	  {
		  return Array[rear];
	  }
  }
  
  public int size(){
    return this.array_size;
  }
  
  public boolean isEmpty(){
    return ( this.array_size == 0 );
  }

  public String toString(){
	  StringBuffer temp = new StringBuffer();
	  temp.append('[');
	  
	  for(int i = 0; i < this.size() ; i++ )
	  {
		  temp.append(Array[(front + i)%N].toString());
		  if( i != this.size()-1)
			  temp.append(",");
	  }
	  
	  temp.append(']');
//	  temp.append(",Front=");
//	  temp.append(front);
//	  temp.append(",Rear=");
//	  temp.append(rear);
//	  temp.append(",UAsize=");
//	  temp.append(N);
//	  temp.append(",size=");
//	  temp.append(this.array_size);
	  return temp.toString();
  }  
//  public String PRINTTHEUNDERLYINGARRAY() {
//	  StringBuffer temp = new StringBuffer();
//	  temp.append('[');
//	  
//	  for(int i = 0; i < this.array_size ; i++ )
//	  {
//		  temp.append(Array[i].toString());
//		  if( i != N-1)
//			  temp.append(",");
//	  }
//	  return temp.toString();
//  }
}