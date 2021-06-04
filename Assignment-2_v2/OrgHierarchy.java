import java.io.*; 
import java.util.*;

class Node<T>
{
    Integer[] keys;
    Object[] data; // Nodes of DB
    Object[] children;
    Node()
    {
        this.keys = new Integer[4];
        this.data = new Object[4];
        this.children = new Object[5];
    }
    int size()
    {
        int size = 0;
        if( this.keys[0] == null )
            return 0;
        if( this.keys[1] == null)
            size = 1;
        else if( this.keys[2] == null)
            size = 2;
        else if( this.keys[3] == null)
            size = 3;
        else
            size = 4;
        return size;    
    }
    
    boolean isLeaf()
    {
        return (this.children[0]==null);
    }
    
    public String toString()
    {
        return Arrays.toString(this.keys)+ ' ' + Arrays.toString(this.data) ;
    }
}

class ABTree <T>
{
    Node<T> root;

    ABTree(int key,T data)
    {
        this.root = new Node<T>();
        this.root.keys[0] = key;
        this.root.data[0] = data;
        this.root.children[0] = (Node<T>)(null);
        this.root.children[1] = (Node<T>)(null);
    }

    @SuppressWarnings("unchecked")
    Node<T> findNode(Node<T> v,int key)
    {
        if( v == null )
            return null;
        else
        {
            int i = 0;
            for(i = 0; i < v.size() ; i++)
            {
                if( key < v.keys[i] )
                    return findNode((Node<T>)v.children[i],key);
                if( key == v.keys[i] )
                    return v;
            }
            return findNode( (Node<T>)v.children[i], key);
        }
    }
    
    @SuppressWarnings("unchecked")
    T findData(Node<T> v,int key)
    {
        if( v == null )
            return null;
        Node<T>pos = this.findpos(this.root, key);
//        System.out.println(pos);
        if (pos == null)
        {
            return null;
        }
        for(int i = 0;i<pos.size();i++)
        {
            if (pos.keys[i] == key)
            {
                return (T) pos.data[i];
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    Node<T> findpos(Node<T> v,int key)
    {
        if (v==null)
        {
            return null;
        }
        int i = 0;
        for( i = 0; i < v.size() ; i++ )
        {
            if( key < v.keys[i] )
            {
                return findpos((Node<T>)v.children[i],key);
            }
            else if (key == v.keys[i])
            {
                return v;
            }
        }
        return findpos((Node<T>)v.children[v.size()],key);
    }
    
    
    
//    insert(root,...,..)
    @SuppressWarnings("unchecked")
    void insert(Node<T> v,int key,T data)
    {
//      System.out.println("***************************");
//      this.PrintTree(this.root);
        int size = v.size();
        boolean isLeaf = v.isLeaf();
        
        if(isLeaf)
        {
            int i = 0;
            boolean inserted = false;
            for( i = 0; i < size ; i++ )
            {
                if( key < v.keys[i] )
                {
                    //[10,15,20]
                    //[10,11,15,20]
                    v.children[v.size()+1] = v.children[v.size()];
                    for(int j = size-1; j >=i ; j-- )
                    {
                        v.keys[j+1] = v.keys[j];
                        v.data[j+1] = v.data[j];
                        v.children[j+1] = null;
                    }
                    
                    v.keys[i] = key;
                    v.data[i] = data;
                    v.children[i] = null;
                    inserted = true;
                    break;
                }
            }
            
            if(!inserted)
            {
                v.keys[i] = key;
                v.data[i] = data;
                v.children[i] = null;
            }
            
            if( v == root && v.size() == 4)
            {
                Node<T> new_node = new Node<T>();
                
                Node<T> nodetosplit = v;
                
                
                new_node.keys[0] = nodetosplit.keys[2];
                new_node.keys[1] = nodetosplit.keys[3];
                new_node.children[0] = nodetosplit.children[2];
                new_node.children[1] = nodetosplit.children[3];
                new_node.children[2] = nodetosplit.children[4];
                new_node.data[0] = nodetosplit.data[2];
                new_node.data[1] = nodetosplit.data[3];
                
                // reuse
                nodetosplit.keys[2] = null;
                nodetosplit.keys[3] = null;
                nodetosplit.data[2] = null;
                nodetosplit.data[3] = null;
                nodetosplit.children[2] = null;
                nodetosplit.children[3] = null;
                nodetosplit.children[4] = null;
                
                Node<T> new_root = new Node<T>();
                
                new_root.keys[0] = nodetosplit.keys[1];
                new_root.data[0] = nodetosplit.data[1];
                new_root.children[0] = nodetosplit;
                new_root.children[1] = new_node;
                nodetosplit.keys[1]=null;
                nodetosplit.data[1] = null;
                this.root = new_root;
//              System.out.println("***************************");
//              this.PrintTree(this.root);
                return;
            }
        }
        ////////////////////////////////////////////////////
        /////////////////////////////////////////////////
        // [1,2,3,4]
        else
        {
            int i = 0;
            boolean inserted = false;
            
            for(i=0; i< size;i++)
            {
                if(key < v.keys[i])
                {
                    inserted = true;
                    insert((Node<T>)v.children[i],key,data);
                    // balance
                    Node<T> insertedin = (Node<T>)(v.children[i]);
  
                    if( insertedin.size() == 4)
                    {
                        
                        Node<T> new_node = new Node<T>();
                        
                        Node<T> nodetosplit = (Node<T>)(v.children[i]);
                        
                        
                        new_node.keys[0] = nodetosplit.keys[2];
                        new_node.keys[1] = nodetosplit.keys[3];
                        new_node.children[0] = nodetosplit.children[2];
                        new_node.children[1] = nodetosplit.children[3];
                        new_node.children[2] = nodetosplit.children[4];
                        new_node.data[0] = nodetosplit.data[2];
                        new_node.data[1] = nodetosplit.data[3];
                        
                        // reuse
                        nodetosplit.keys[2] = null;
                        nodetosplit.keys[3] = null;
                        nodetosplit.data[2] = null;
                        nodetosplit.data[3] = null;
                        nodetosplit.children[2] = null;
                        nodetosplit.children[3] = null;
                        nodetosplit.children[4] = null;
                        
                        // Now insert nodetosplit.key[1] to v.
                        int j =0;
                        boolean temp = false;
                        int temp_size = v.size();
                        for(j = 0; j < temp_size;j++)
                        {
                            if( nodetosplit.keys[1] < v.keys[j] )
                            {
                                temp = true;
                                v.children[v.size()+1] = v.children[v.size()];
                                for(int k=v.size()-1;k>=j ;k--)
                                {
                                    v.keys[k+1] = v.keys[k];
                                    v.data[k+1] = v.data[k];
                                    v.children[k+1] = v.children[k];
                                }
                                v.keys[j] = nodetosplit.keys[1];
                                v.data[j] = nodetosplit.data[1];
                                v.children[j] = nodetosplit;
                                v.children[j+1] = new_node;
                                nodetosplit.keys[1] = null;
                                nodetosplit.data[1] = null;
                                break;
                            }
                        }
                        if(!temp)
                        {
                            v.keys[j] = nodetosplit.keys[1];
                            v.data[j] = nodetosplit.data[1];
                            v.children[j] = nodetosplit;
                            v.children[j+1] = new_node;
                            nodetosplit.keys[1] = null;
                            nodetosplit.data[1] = null;
                        }
                        
                        if( v.size()==4 && root == v)
                        {
                            // now split root manually as it has no parents.
                            new_node = new Node<T>();
                            
                            nodetosplit = v;
                            
                            
                            new_node.keys[0] = nodetosplit.keys[2];
                            new_node.keys[1] = nodetosplit.keys[3];
                            new_node.children[0] = nodetosplit.children[2];
                            new_node.children[1] = nodetosplit.children[3];
                            new_node.children[2] = nodetosplit.children[4];
                            new_node.data[0] = nodetosplit.data[2];
                            new_node.data[1] = nodetosplit.data[3];
                            
                            // reuse
                            nodetosplit.keys[2] = null;
                            nodetosplit.keys[3] = null;
                            nodetosplit.data[2] = null;
                            nodetosplit.data[3] = null;
                            nodetosplit.children[2] = null;
                            nodetosplit.children[3] = null;
                            nodetosplit.children[4] = null;
                            
                            Node<T> new_root = new Node<T>();
                            new_root.keys[0] = nodetosplit.keys[1];
                            new_root.data[0] = nodetosplit.data[1];
                            new_root.children[0] = nodetosplit;
                            new_root.children[1] = new_node;
                            nodetosplit.keys[1]=null;
                            nodetosplit.data[1] = null;
                            this.root = new_root;
//                          System.out.println("***************************");
//                          this.PrintTree(this.root);
                            return;
                        }
                    }
                    break;
                }
            }
            if(!inserted)
            {
                insert((Node<T>)v.children[i],key,data);
                // balance
                Node<T> insertedin = (Node<T>)(v.children[i]);
    
                if( insertedin.size() == 4 )
                {
                    
                    Node<T> new_node = new Node<T>();
                    
                    Node<T> nodetosplit = (Node<T>)(v.children[i]);
                    
                    new_node.keys[0] = nodetosplit.keys[2];
                    new_node.keys[1] = nodetosplit.keys[3];
                    new_node.children[0] = nodetosplit.children[2];
                    new_node.children[1] = nodetosplit.children[3];
                    new_node.children[2] = nodetosplit.children[4];
                    new_node.data[0] = nodetosplit.data[2];
                    new_node.data[1] = nodetosplit.data[3];
                    
                    // reuse
                    nodetosplit.keys[2] = null;
                    nodetosplit.keys[3] = null;
                    nodetosplit.data[2] = null;
                    nodetosplit.data[3] = null;
                    nodetosplit.children[2] = null;
                    nodetosplit.children[3] = null;
                    nodetosplit.children[4] = null;
                    
//                  System.out.println(new_node);
//                  System.out.println(nodetosplit);
                    
                    // Now insert nodetosplit.key[1] to v.
                    
                    boolean temp = false;
                    int j =0;
                    int temp_size = v.size();
                    for(j = 0; j< temp_size;j++)
                    {
                        if( nodetosplit.keys[1] < v.keys[j] )
                        {
                            temp = true;
                            v.children[v.size()+1] = v.children[v.size()];
                            for(int k=v.size()-1;k>=j ;k--)
                            {
                                v.keys[k+1] = v.keys[k];
                                v.data[k+1] = v.data[k];
                                v.children[k+1] = v.children[k];
                            }
                            v.keys[j] = nodetosplit.keys[1];
                            v.data[j] = nodetosplit.data[1];
                            v.children[j] = nodetosplit;
                            v.children[j+1] = new_node;
                            nodetosplit.keys[1] = null;
                            nodetosplit.data[1] = null;
                            break;
                        }
                    }
                    if(!temp)
                    {
                        v.keys[j] = nodetosplit.keys[1];
                        v.data[j] = nodetosplit.data[1];
                        v.children[j] = nodetosplit;
                        v.children[j+1] = new_node;
                        nodetosplit.keys[1] = null;
                        nodetosplit.data[1] = null;
                    }
                    
//                  System.out.println(v);
                    
                    if ( v.size()==4 && root == v)//root split and 
                    {
                        // now split root manually as it has no parents.
                        new_node = new Node<T>();
                        
                        nodetosplit = v;
                        
                        
                        new_node.keys[0] = nodetosplit.keys[2];
                        new_node.keys[1] = nodetosplit.keys[3];
                        new_node.children[0] = nodetosplit.children[2];
                        new_node.children[1] = nodetosplit.children[3];
                        new_node.children[2] = nodetosplit.children[4];
                        new_node.data[0] = nodetosplit.data[2];
                        new_node.data[1] = nodetosplit.data[3];
                        
                        // reuse
                        nodetosplit.keys[2] = null;
                        nodetosplit.keys[3] = null;
                        nodetosplit.data[2] = null;
                        nodetosplit.data[3] = null;
                        nodetosplit.children[2] = null;
                        nodetosplit.children[3] = null;
                        nodetosplit.children[4] = null;
                        
                        Node<T> new_root = new Node<T>();
                        new_root.keys[0] = nodetosplit.keys[1];
                        new_root.data[0] = nodetosplit.data[1];
                        new_root.children[0] = nodetosplit;
                        new_root.children[1] = new_node;
                        nodetosplit.keys[1]=null;
                        nodetosplit.data[1] = null;
                        this.root = new_root;
//                      System.out.println("***************************");
//                      this.PrintTree(this.root);
                        return;
                    }
                }
            }
        }
        
        // insert ends here.
    }
    
    @SuppressWarnings("unchecked")
    Node<T> findpred(Node<T> v)
    {
        if( v.isLeaf())
            return v;
        return findpred((Node<T>)v.children[v.size()]);
    }
    
    
    @SuppressWarnings("unchecked")
    void delete(Node<T> v,int key)
    {
        int size = v.size();
        
        boolean isLeaf = v.isLeaf();
        
        
        // assert size <=3.
        
        if(isLeaf)
        {
            int i = 0;
            boolean deleted = false;
            
            for(i = 0; i < v.size(); i++)
            {
                if( key == v.keys[i] )
                {
                    // found in the node
                    int j = i;
                    for(j = i;j < size-1;j++)
                    {
                        v.keys[j] = v.keys[j+1];
                        v.data[j] = v.data[j+1];
                        // is leaf so no children.
                    }
                    // j = size-1;
                    v.keys[j] = null;
                    v.data[j] = null;
                    deleted = true;
                    if( v == this.root && v.size() == 0 )
                    {
                        this.root = null;
                    }
                    return;
                }
            }
            if(!deleted)
            {
                System.err.println("Not found "+key);
                return;
            }
            // Leaf case ends here.
        }
        
        else
        {
            int i = 0;
            boolean deleted = false;
            
            for(i = 0; i < size; i++)
            {
                if( key == v.keys[i] )
                {
                    deleted = true;
                    // delete key from v.keys[i] and replace with predecessor.
                    // Since this is not leaf, I can just find the position of key.
                    Node<T> PredNode = findpred((Node<T>)v.children[i]);
                    int str1 = PredNode.size()-1;
                    int temp_key = PredNode.keys[str1]; // Save predecessor key
                    Object temp_data = PredNode.data[str1];// Save predecessor data
                    
                    this.delete((Node<T>)v.children[i], temp_key); // Delete the last key of PredNode, can be empty needs to merged.
                    v.keys[i] = temp_key;
                    v.data[i] = temp_data; 
                    Node<T>deletedfrom =(Node<T>) v.children[i];
                    if( deletedfrom.size() == 0 ) 
                    {   
                        // Now need to merge from sibling. either i-1 or i+1 and v.
                        if( i == 0 )
                        {
                            // only right sibling possible.
                            Node<T> right_sibling = (Node<T>)v.children[i+1];
                            
                            if( right_sibling.size() != 1 )
                            {
                                // borrowing from parent
                                deletedfrom.keys[0] = v.keys[i];
                                deletedfrom.data[0] = v.data[i];
                                
                                // parent borrowing from sibling.
                                v.keys[i] = right_sibling.keys[0];
                                v.data[i] = right_sibling.data[0];
                                
                                // Taking the child of your sibling.
                                deletedfrom.children[1] = right_sibling.children[0];
                                
                                // sibling changing.
                                int j = 0;
                                for(j = 0;j < right_sibling.size()-1;j++)
                                {
                                    right_sibling.keys[j] = right_sibling.keys[j+1];
                                    right_sibling.data[j] = right_sibling.data[j+1];
                                    right_sibling.children[j] = right_sibling.children[j+1];
                                }
                                // j = size-1;
                                right_sibling.children[j] = right_sibling.children[j+1];
                                right_sibling.children[j+1] = null;
                                right_sibling.keys[j] = null;
                                right_sibling.data[j] = null;
                            }
                            else
                            {
                                // borrowing from parent
                                deletedfrom.keys[0] = v.keys[i];
                                deletedfrom.data[0] = v.data[i];
                                
                                //killing your sibling and merging
                                deletedfrom.keys[1] = right_sibling.keys[0];
                                deletedfrom.data[1] = right_sibling.data[0];
                                
                                deletedfrom.children[1] = right_sibling.children[0];
                                deletedfrom.children[2] = right_sibling.children[1];
                                
                                //Shifting parent.
                                int j = i;
                                for(j = i;j < v.size()-1;j++)
                                {
                                    v.keys[j] = v.keys[j+1];
                                    v.data[j] = v.data[j+1];
                                    v.children[j+1] = v.children[j+2];
                                }
                                v.keys[j] = null;
                                v.data[j] = null;
                                v.children[j+1] = null; // i = 1
                                if( v == this.root && v.size() == 0 )
                                {
                                    this.root = (Node<T>) v.children[0];
                                }
                            }
                        }
                        else if( i == 1 || i == 2 )
                        {
                            // i == 2
                            // two siblings
                            Node<T> left_sibling = (Node<T>)v.children[i-1];//Good 
                            Node<T> right_sibling = (Node<T>)v.children[i+1];//Good
                            
                            if( left_sibling.size()>1 )
                            {
                                //borrowing from parent
                                deletedfrom.keys[0] = v.keys[i-1];
                                deletedfrom.data[0] = v.data[i-1];
                                
                                // parent borrowing from sibling.
                                int str2 = left_sibling.size()-1;
                                v.keys[i-1] = left_sibling.keys[str2];
                                v.data[i-1] = left_sibling.data[str2];
                                
                                // Taking the child of your sibling.
                                deletedfrom.children[1] = deletedfrom.children[0];
                                deletedfrom.children[0] = left_sibling.children[left_sibling.size()];
                                
                                int store_size = left_sibling.size();
                                
                                left_sibling.children[store_size] = null;
                                left_sibling.keys[store_size-1] = null;
                                left_sibling.data[store_size-1] = null;
                            }
                            else if( right_sibling.size()>1 )
                            {
                                
                                // borrowing from parent
                                deletedfrom.keys[0] = v.keys[i];
                                deletedfrom.data[0] = v.data[i];
                                
                                // parent borrowing from sibling.
                                v.keys[i] = right_sibling.keys[0];
                                v.data[i] = right_sibling.data[0];
                                
                                // Taking the child of your sibling.
                                deletedfrom.children[1] = right_sibling.children[0];
                                
                                // sibling changing.
                                int j = 0;
                                for(j = 0;j < right_sibling.size()-1;j++)
                                {
                                    right_sibling.keys[j] = right_sibling.keys[j+1];
                                    right_sibling.data[j] = right_sibling.data[j+1];
                                    right_sibling.children[j] = right_sibling.children[j+1];
                                }
                                // j = size-1;
                                right_sibling.children[j] = right_sibling.children[j+1];
                                right_sibling.children[j+1] = null;
                                right_sibling.keys[j] = null;
                                right_sibling.data[j] = null;
                            }
                            else
                            {
                                left_sibling.keys[1] = v.keys[i-1];
                                left_sibling.data[1] = v.data[i-1];
                                
                                left_sibling.children[2] = deletedfrom.children[0];
                                
//                              v.children[i+1] = null;
//                              v.keys[i] = null;
//                              v.data[i] = null;
                                //Deleted from left => My all nodes whill shift from 1->0 2->1 and so one
                                // My all children will shift from 2->1 , 3->2 and so on
                                int x = 0;
                                for (x = i;x<3;x++)
                                {
                                    v.keys[x-1] = v.keys[x];
                                    v.data[x-1] = v.data[x];
                                    v.children[x] = v.children[x+1];
                                }
                                v.keys[x-1] = null;
                                v.data[x-1] = null;
                                v.children[x] = null;
                                
//                              left_sibling.children[2] = deletedfrom.children[0];
                                if( v == this.root && v.size() == 0 )
                                {
                                    this.root = (Node<T>) v.children[0];
                                }
                            }
                        }
                    }
//                  System.out.println("First "+v.keys[i]+" "+v.data[i]);
//                  System.out.println("Updated"+v.keys[i]+" "+v.data[i]);
                    return;
                }
                else
                if( key < v.keys[i] )
                {
                    deleted = true;
                    //key maybe on the children i, recursively delete from that children.
                    delete((Node<T>)v.children[i], key);
                    
                    Node<T> deletedfrom = (Node<T>)(v.children[i]);
//                  System.out.println(deletedfrom.size()+"HEMLP"+Integer.toString(i));
                    if( deletedfrom.size() == 0 ) 
                    {   
                        // Now need to merge from sibling. either i-1 or i+1 and v.
                        if( i == 0 )
                        {
                            // only right sibling possible.
                            Node<T> right_sibling = (Node<T>)v.children[i+1];
                            
                            if( right_sibling.size() != 1 )
                            {
                                // borrowing from parent
                                deletedfrom.keys[0] = v.keys[i];//OK
                                deletedfrom.data[0] = v.data[i];//OK
                                
                                // parent borrowing from sibling.
                                v.keys[i] = right_sibling.keys[0];//OK
                                v.data[i] = right_sibling.data[0];
                                
                                // Taking the child of your sibling.
                                deletedfrom.children[1] = right_sibling.children[0];//OK
                                
                                // sibling changing.
                                int j = 0;
                                for(j = 0;j < right_sibling.size()-1;j++)
                                {
                                    right_sibling.keys[j] = right_sibling.keys[j+1];
                                    right_sibling.data[j] = right_sibling.data[j+1];
                                    right_sibling.children[j] = right_sibling.children[j+1];
                                }
                                // j = size-1;
                                right_sibling.children[j] = right_sibling.children[j+1];
                                right_sibling.children[j+1] = null;
                                right_sibling.keys[j] = null;
                                right_sibling.data[j] = null;
                            }
                            else
                            {
                                // borrowing from parent
                                deletedfrom.keys[0] = v.keys[i];
                                deletedfrom.data[0] = v.data[i];
                                
                                //killing your sibling and merging
                                deletedfrom.keys[1] = right_sibling.keys[0];
                                deletedfrom.data[1] = right_sibling.data[0];
                                
                                deletedfrom.children[1] = right_sibling.children[0];
                                deletedfrom.children[2] = right_sibling.children[1];
                                
                                //Shifting parent.
                                int j = i;
                                for(j = i;j < v.size()-1;j++)
                                {
                                    v.keys[j] = v.keys[j+1];
                                    v.data[j] = v.data[j+1];
                                    v.children[j+1] = v.children[j+2];
                                }
                                v.keys[j] = null;
                                v.data[j] = null;
                                v.children[j+1] = null; // i = 1
                                if( v == this.root && v.size() == 0 )
                                {
                                    this.root = (Node<T>) v.children[0];
                                }
                            }
                        }
                        else if( i == 1 || i == 2 )
                        {
                            
                            // i == 2
                            // two siblings
                            Node<T> left_sibling = (Node<T>)v.children[i-1];
                            Node<T> right_sibling = (Node<T>)v.children[i+1];
                            
                            if( left_sibling.size()!=1 )
                            {
                                //borrowing from parent
                                deletedfrom.keys[0] = v.keys[i-1];
                                deletedfrom.data[0] = v.data[i-1];
                                
                                // parent borrowing from sibling.
                                int str = left_sibling.size()-1;
                                v.keys[i-1] = left_sibling.keys[str];
                                v.data[i-1] = left_sibling.data[str];
                                    
                                // Taking the child of your sibling.
                                deletedfrom.children[1] = deletedfrom.children[0];
                                deletedfrom.children[0] = left_sibling.children[left_sibling.size()];
                                
                                int store_size = left_sibling.size();
                                
                                left_sibling.children[store_size] = null;
                                left_sibling.keys[store_size-1] = null;
                                left_sibling.data[store_size-1] = null;
                            }
                            else if( right_sibling.size()!=1 )
                            {
                                
                                // borrowing from parent
                                deletedfrom.keys[0] = v.keys[i];
                                deletedfrom.data[0] = v.data[i];
                                
                                // parent borrowing from sibling.
                                v.keys[i] = right_sibling.keys[0];
                                v.data[i] = right_sibling.data[0];
                                
                                // Taking the child of your sibling.
                                deletedfrom.children[1] = right_sibling.children[0];
                                
                                // sibling changing.
                                int j = 0;
                                for(j = 0;j < right_sibling.size()-1;j++)
                                {
                                    right_sibling.keys[j] = right_sibling.keys[j+1];
                                    right_sibling.data[j] = right_sibling.data[j+1];
                                    right_sibling.children[j] = right_sibling.children[j+1];
                                }
                                // j = size-1;
                                right_sibling.children[j] = right_sibling.children[j+1];
                                right_sibling.children[j+1] = null;
                                right_sibling.keys[j] = null;
                                right_sibling.data[j] = null;
                            }
                            else
                            {
                                left_sibling.keys[1] = v.keys[i-1];
                                left_sibling.data[1] = v.data[i-1];
                                
                                left_sibling.children[2] = deletedfrom.children[0];
                                
//                              v.children[i+1] = null;
//                              v.keys[i] = null;
//                              v.data[i] = null;
                                int x = 0;
                                for (x = i;x<3;x++)
                                {
                                    v.keys[x-1] = v.keys[x];
                                    v.data[x-1] = v.data[x];
                                    v.children[x] = v.children[x+1];
                                }
                                v.keys[x-1] = null;
                                v.data[x-1] = null;
                                v.children[x] = null;
                                
//                              left_sibling.children[2] = deletedfrom.children[0];
                                if( v == this.root && v.size() == 0 )
                                {
                                    this.root = (Node<T>) v.children[0];
                                }
                            }
                        }
                    }
                    return;
                }
            }
            if(!deleted)
            {
                delete((Node<T>)v.children[i], key);
                
                Node<T> deletedfrom = (Node<T>)(v.children[i]);
                
                if( deletedfrom.size() == 0 ) 
                {
                    // Now need to merge from sibling. either i-1 or i+1 and v.
                    // only left sibling
                    Node<T> left_sibling = (Node<T>)v.children[i-1];
                    
                    if(left_sibling.size()!=1)
                    {
                        // left child can give
                        //borrowing from parent
                        deletedfrom.keys[0] = v.keys[i-1];
                        deletedfrom.data[0] = v.data[i-1];
                        
                        // parent borrowing from sibling.
                        v.keys[i-1] = left_sibling.keys[left_sibling.size()-1];
                        v.data[i-1] = left_sibling.data[left_sibling.size()-1];
                        
                        // Taking the child of your sibling.
                        deletedfrom.children[1] = deletedfrom.children[0];
                        deletedfrom.children[0] = left_sibling.children[left_sibling.size()];
                        
                        int store_size = left_sibling.size();
                        
                        left_sibling.children[store_size] = null;
                        left_sibling.keys[store_size-1] = null;
                        left_sibling.data[store_size-1] = null;
                    }
                    else
                    {
                        left_sibling.keys[1] = v.keys[i-1];
                        left_sibling.data[1] = v.data[i-1];
                        
                        left_sibling.children[2] = deletedfrom.children[0];
                        
                        v.children[i] = null;
                        v.keys[i-1] = null;
                        v.data[i-1] = null;
                        
//                      left_sibling.children[2] = deletedfrom.children[0];
                        if( v == this.root && v.size() == 0 )
                        {
                            this.root = (Node<T>) v.children[0];
                        }
                    }
                }
            }
            
            // Not leaf case ends here.
        }
    }
    public void deletekey(Integer key)
    {
        this.delete(this.root, key);
    }
    public void insert_key_value(Integer key, T data)
    {
        this.insert(this.root, key, data);
    }
    @SuppressWarnings("unchecked")
    public void SortTree(Node<T>v)
    {
        if (v==null)
        {
            return;
        }
        for (int i = 0;i<4;i++)
        {
            SortTree((Node<T>)v.children[i]);
            if (i<3 &&v.keys[i]!=null)
            {
                System.out.println(v.keys[i]);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void stringit(Node<T> v,StringBuffer s)
    {
        if (v==null)
        {
            return;
        }
        for(int i = 0;i<4;i++)
        {
            stringit((Node<T>)v.children[i],s);
            if (i<3 && v.keys[i]!=null)
            {
                
                if (s.length()== 0)
                {
                    s.append(v.keys[i].toString());
                }
                else
                {
                    s.append(" "+v.keys[i].toString());
                }
                    
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void PrintTree(Node<T>v)
    {
        if (v==null)
        {
            return;
        }
        for (int i = 0;i<4;i++)
        {
            PrintTree((Node<T>)v.children[i]);
        }
        System.out.println(v);
    }
    public String toString()
    {
        StringBuffer s =new StringBuffer(); 
        this.stringit(this.root,s);
        return s.toString();
    }
}
//LeftMost -> Key1  -> right

// Linked List implementation
class LLNode<T>
{
    T data;
    LLNode <T>next;
    public LLNode()
    
    {
        this.data = null;
        this.next = null;
    }
}
class LinkedList<T>
{
    LLNode<T> head;
    LLNode<T> tail;
    int size = 0;
    public LinkedList()
    {
        this.head=null;
        this.tail = null;
        this.size = 0;
    }
    
    public void insert(T data)
    {
        if (this.size == 0)
        {
            this.head = new LLNode<T>();
            this.head.data = data;
            this.tail = this.head;
        }
        else
        {
            this.tail.next = new LLNode<>();
            this.tail.next.data = data;
            this.tail = this.tail.next;
        }
        this.size++;
    }
    public void delete(T data)
    {
        LLNode<T> trav = this.head;
        if (this.size == 0)
        {
            return;
        }
        if (this.head.data == data)
        {
            this.size--;
            this.head = this.head.next;
            return;
        }
        else
        {
            while (trav.next != null)
            {
                if (trav.next.data == data)
                {
                    trav.next = trav.next.next;
                    this.size--;
                    return;
                }
                trav = trav.next;
            }
        }
    }
    public String toString()
    {
        String s = "[";
        LLNode<T> trav = this.head;
        while (trav!=this.tail)
        {
            s+=trav.data.toString()+",";
            trav = trav.next;
        }
        s+=this.tail.data.toString()+"]";
        return s;
    }
}

// Organization Tree node
class OrgNode {
    int id;
    LinkedList<OrgNode>children;
    int level;
    OrgNode boss;
    public OrgNode(int id, OrgNode boss)
    {
        this.id = id;
        this.boss = boss;
        this.children = new LinkedList<>();
        if (boss == null)
        {
            this.level = 1;
        }
        else
        {
            this.level = this.boss.level+1;
        }
    }
    public boolean isOwner()
    {
        return this.boss == null;
    }
    public void insertChild(OrgNode child)
    {
        this.children.insert(child);
    }
    public String toString()
    {
        return "Orgnode: "+Integer.toString(this.id);
    }
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
OrgNode Owner;
ABTree<OrgNode> Map;
int size = 0;

// find node helper function

public boolean isEmpty()
{
    return this.size == 0;
} 

public int size()
{
    return size;
}

public int level(int id) throws IllegalIDException, EmptyTreeException
{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if ( isEmpty() )
        throw new EmptyTreeException("Tree is empty");
    OrgNode node = this.Map.findData(this.Map.root, id);
    if ( node == null )
        throw new IllegalIDException("ID not found in the Database");
    else
        return node.level;
} 

public void hireOwner(int id) throws NotEmptyException{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if(!this.isEmpty())
        throw new NotEmptyException("Owner already present");
    this.Owner = new OrgNode(id,null);
    this.Map = new ABTree<OrgNode>(id, this.Owner);
    this.size = 1;
}

public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if ( isEmpty() )
        throw new EmptyTreeException("Tree is empty");
    OrgNode boss = this.Map.findData(this.Map.root, bossid);
    if ( boss == null )
        throw new IllegalIDException("ID not found in the Database");
    else
    {
        OrgNode new_emp = new OrgNode(id,boss); // find is boss
        this.Map.insert_key_value(id, new_emp);
        boss.children.insert(new_emp);
        this.size++;
    }   
} 

public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
    //your implementation
//  throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if ( isEmpty() )
        throw new EmptyTreeException("Tree is empty");
    OrgNode find = this.Map.findData(this.Map.root,id);
    if ( find == null )
        throw new IllegalIDException("ID not found in the Database");
    else if (find.children.size != 0 || find == this.Owner ) // Size of the first Linkedlist of find not empty.
        throw new IllegalIDException("This ID has children. Illegal ID removal");
    else
    {
        find.boss.children.delete(find);
        this.Map.deletekey(find.id);
        this.size--;
        return;
    }
}
public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if ( isEmpty() )
        throw new EmptyTreeException("Tree is empty");
    OrgNode find = this.Map.findData(this.Map.root, id);
    OrgNode find_coworker = this.Map.findData(this.Map.root, manageid);
    if ( find == null || find_coworker == null || find.level != find_coworker.level )
        throw new IllegalIDException("ID not found in the Database");
    else if ( find == this.Owner )
        throw new IllegalIDException("This ID is the owner. Cannot remove Owner");
    else
    {
        LLNode<OrgNode>trav = find.children.head; // Iterator for LinkedList of LinkedLists of find.
        // i.e. Looping over the LinkedLists of children of find.
        while( trav!=null )
        {
            find_coworker.children.insert(trav.data);
            trav.data.boss = find_coworker; // Changing parent of each LinkedLists of find.
            // will self-awareness also change?? No I am inserting Whole LinkedLists to another employee only.
            trav = trav.next;
        }
        this.Map.deletekey(id);
        this.size--;
    }
    find.boss.children.delete(find);
    return;
} 

public int boss(int id) throws IllegalIDException,EmptyTreeException{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    if ( isEmpty() )
        throw new EmptyTreeException("Tree is empty");
    OrgNode find = this.Map.findData(this.Map.root, id);
    if ( find == null )
        throw new IllegalIDException("ID not found in the Database");
    else if ( find == this.Owner )
        throw new IllegalIDException("Owner has no boss");
    else
        return find.boss.id;
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
     OrgNode Emp1 = this.Map.findData(this.Map.root, id1);
     OrgNode Emp2 = this.Map.findData(this.Map.root, id2);
     
     if( Emp1 == null || Emp2 == null )
         throw new IllegalIDException("ID not found in the Database");
//   System.out.println(Integer.toString(Emp1.id)+","+Integer.toString(Emp2.id));
     if( Emp1.level > Emp2.level )
     {
         while( Emp1.level != Emp2.level )
         {
//           System.out.println(Integer.toString(Emp1.id)+","+Integer.toString(Emp2.id));
             Emp1 = Emp1.boss;
             if( (Emp1 != null) && (Emp2 != null) )
                     break;
         }
     }
     else
     {
         while( Emp1.level != Emp2.level )
         {
//           System.out.println(Integer.toString(Emp1.id)+","+Integer.toString(Emp2.id));
             Emp2 = Emp2.boss;
             if( (Emp1 != null) && (Emp2 != null) )
                 break;
         }
     }
     // Emp1 and Emp2 at same level now.
     while( Emp1 != Emp2 )
     {
//       System.out.println(Integer.toString(Emp1.id)+","+Integer.toString(Emp2.id));
         Emp1 = Emp1.boss;
         Emp2 = Emp2.boss;
         if( (Emp1 != null) && (Emp2 != null) )
             break;
     }
     return Emp1.id;
}

public void stringer(OrgNode v,Vector<ABTree<Integer>> Sort_trees,int offset)
{
    if (v==null)
    {
        return;
    }
    if (v.level - offset==Sort_trees.size())
    {
        Sort_trees.add(new ABTree<>(v.id, null));
    }
    else
    {
        Sort_trees.get(v.level-offset).insert_key_value(v.id,null);
    }
    LLNode<OrgNode>trav = v.children.head;
    while (trav!=null)
    {
        stringer(trav.data, Sort_trees,offset);
        trav = trav.next;
    }
//  LinkedListNode<LinkedList<OrgNode>> children_linked_list = v.children.header.next; // Iterator for LinkedList of LinkedLists of find.
//  // i.e. Looping over the LinkedLists of children of find.
//  if(children_linked_list == null)
//      return;
//  
//  while( children_linked_list!= v.children.trailer )
//  {
//      if( children_linked_list.data.header.next == children_linked_list.data.trailer )
//      {
//          // Linked List is empty
//          children_linked_list = children_linked_list.next;
//          continue;
//      }
//      LinkedListNode<OrgNode> children = children_linked_list.data.header.next;
//      while( children != children_linked_list.data.trailer )
//      {
//          stringer(children.data, Sort_trees,offset);
//          children = children.next;           
//      }
//      children_linked_list = children_linked_list.next;
//  }
    
}

public String toString(int id) throws IllegalIDException, EmptyTreeException{
    //your implementation
//   throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    StringBuffer s = new StringBuffer();
    
    OrgNode find = this.Map.findData(this.Map.root, id);
    
    
    if ( find == null )
        throw new IllegalIDException("ID not found in the Database");
    
    Vector<ABTree<Integer>> Sort_trees = new Vector<>();
    stringer(find,Sort_trees,find.level);
    
//  s.append( Integer.toString(find.id) + "," );
    
    for(ABTree<Integer> tree: Sort_trees)
    {
        s.append(tree.toString());
        s.append(",");
    }
    if(s.length()>1)
        s.deleteCharAt(s.length()-1);
    
    return s.toString();     
}
public static void main(String[]args)
{
 OrgHierarchy My_Company = new OrgHierarchy();
 try
 {
     My_Company.hireOwner(1);
 }
 catch(Exception e)
 {
     e.printStackTrace();
 }
 Random rand = new Random();
 Vector<Integer> temp = new Vector();
 temp.add(1);
 int max_level = 0;
 while (temp.size()!=10000)
 {
     int id = (rand.nextInt(1000000)%temp.size()+temp.size())%temp.size();
     int ins = rand.nextInt(10000)+2;
     if (My_Company.Map.findNode(My_Company.Map.root,ins)!=null) {
         continue;
         }
     temp.add(ins);
     
     try
     {
         My_Company.hireEmployee( ins,temp.get(id) );
         Node<OrgNode> ans =  My_Company.Map.findNode(My_Company.Map.root,ins);
         for (int i = 0;i<ans.size();i++)
         {
             OrgNode x = (OrgNode)ans.data[i];
             if(max_level<x.level)
             {
                 max_level = x.level;
             }
         }
//            System.out.println("Success in hiring employee id "+ins+" as the junior of "+ temp.get(id));
     }
     catch(Exception e)
     {
         e.printStackTrace();
     }
 }
 try
 {
     System.out.println(My_Company.toString(1));
 }
 catch(Exception e)
 {
     e.printStackTrace();
 }
 
 My_Company.Map.PrintTree(My_Company.Map.root);
 System.out.println("Maxlevel is "+max_level);
}

}
