import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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

class Queue_Heap
{
    // We dont need deletemin
    ArrayList<LinkedList<Customer>>heap;
    int size = 0;
    Queue_Heap(int k)
    {
        this.heap  = new ArrayList<>();
        this.heap.add(null);
        for (int i = 1;i<=k;i++)
        {
            this.heap.add(new LinkedList<Customer>(i));
            this.heap.get(i).heapIndex = i;
        }
        this.size = k;
        //Already Sorted
    }
    public int Parent(int i)
    {
        return i/2;
    }
    public int LeftChild(int i )
    {
        return 2*i;
    }
    public int RightChild(int i )
    {
        return 2*i+1;
    }
    public void BubbleDown(int id)
    {
//      System.out.println("Bubbling Down");
//      System.out.println(this.heap);
        int LeftIndex = this.LeftChild(id);
        int RightIndex = this.RightChild(id);
        int maxIndex = id;
        if (LeftIndex <= this.size && this.heap.get(maxIndex).compareTo(this.heap.get(LeftIndex))>0)
        {
//          System.out.println("YES");
            maxIndex = LeftIndex;
            
        }
        if (RightIndex <= this.size && this.heap.get(maxIndex).compareTo(this.heap.get(RightIndex))>0)
        {
//          System.out.println("YEEEEEEs");
            maxIndex = RightIndex;
            
        }
        if (maxIndex!= id)
        {
            //Swapping Hogi
            LinkedList<Customer> temp  =  this.heap.get(id);
            this.heap.set(id, this.heap.get(maxIndex));
            this.heap.set(maxIndex, temp);
            this.heap.get(maxIndex).heapIndex = maxIndex;
            this.heap.get(id).heapIndex = id;
            BubbleDown(maxIndex);
        }
        return;
    }
    public void BubbleUp(int id)
    {
        int p = this.Parent(id);
        while (p>0 && this.heap.get(id).compareTo(this.heap.get(p))<0)
        {
//          System.out.println(p+"  "+id);
            LinkedList<Customer> temp = this.heap.get(id);
            this.heap.set(id,  this.heap.get(p));
            this.heap.set(p, temp);
            this.heap.get(id).heapIndex = id;
            this.heap.get(p).heapIndex = p;
            id = p;
            p = this.Parent(id);
        }
        return;
    }
    
    public String toString()
    {
        return heap.toString();
    }
    
    public LinkedList<Customer> getMin()
    {
        if(size == 0)
            return null;
        return this.heap.get(1);
    }
    
}


class Tuple<T extends Comparable<T>,V extends Comparable<V>> implements Comparable<Tuple<T,V>>
{
    T first; // will essentially be the time
    V second;
    
    Tuple()
    {
        this.first = null;
        this.second = null;
    }
    
    Tuple(T first,V second)
    {
        this.first = first;
        this.second = second;
    }

    
    @Override
    public int compareTo(Tuple<T, V> arg0) {
        // TODO Auto-generated method stub
        return (this.first.compareTo(arg0.first) == 0)? this.second.compareTo(arg0.second) : this.first.compareTo(arg0.first) ;
    }

    public String toString()
    {
        StringBuffer s = new StringBuffer();
        s.append("(");
        s.append(first.toString());
        s.append(",");
        s.append(second.toString());
        s.append(")");
        return s.toString();
    }
}

class LinkedListNode<T>
{
    T data;
    LinkedListNode<T> next;
    LinkedListNode()
    {
        data = null;
        next = null;
    }
    LinkedListNode(T data,LinkedListNode<T> next)
    {
        this.data = data;
        this.next = next;
    }
}

class LinkedList<T> implements Comparable<LinkedList<T>>
{
    // To use LinkedList as Queue, head = front, tail = rear
    int queue_id;
    int heapIndex;
    LinkedListNode<T> head;
    LinkedListNode<T> tail;
    int size = 0;
    
    LinkedList(int k)
    {
        head = null;
        tail = null;
        queue_id = k;
        size = 0;
    }
    
    void insert(T data)
    {
        LinkedListNode<T> temp = new LinkedListNode<>(data,null);
        if(head == null)
        {
            head = temp;
            tail = temp;
            size+=1;
            return;
        }
        tail.next = temp;
        tail = temp;
        size +=1;
    }
    
    T delete()
    {
        if(head==null)
            return null;
        T temp = head.data;
        head = head.next;
        if(head == null)
        {
            tail = null;
            size = 0;
            return temp;
        }
        size-=1;
        return temp;
    }
    
    LinkedListNode<T> front()
    {
        return this.head;
    }
    
    LinkedListNode<T> rear()
    {
        return this.tail;
    }

    @Override
    public int compareTo(LinkedList<T> arg0) {
        // TODO Auto-generated method stub
//      System.out.println("Arg 0 is "+arg0+"  This is "+this);
        return (this.size - arg0.size == 0 )? this.queue_id - arg0.queue_id : this.size - arg0.size ;
    }
    
    public String toString()
    {
        StringBuffer s = new StringBuffer();
        LinkedListNode<T> x = head;
        s.append("[");
        while(x!=null)
        {
            s.append(x.data);
            s.append(",");
            x = x.next;
        }
        if(s.length()!=1)
            s.deleteCharAt(s.length()-1);
        s.append("]");
        s.append(" queue id: "+this.queue_id);
        s.append(" Heap id: "+this.heapIndex);
        return s.toString();
    }
}

class Event implements Comparable<Event>
{
    int t; // time of the event
    int type; // event type
//  1. Billing specialist prints an order and sends it to the chef; customer leaves the queue.
//  2. A cooked patty is removed from the griddle.
//  3. The chef puts another patty on the griddle.
//  4. A newly arrived customer joins a queue.
//  5. Cooked burgers are delivered to customers.
    Object reference; // Based on event type some information.
    
    Event(int t,int type,Object reference)
    {
        this.t = t;
        this.type = type;
        if(type == 1)
        {
            this.reference = reference;
        }
        else if (type ==2 )
        {
            this.reference = reference;
        }
        else if(type==3)
        {
            this.reference = reference;
        }
        else if(type==4)
        {
            this.reference =reference;
        }
        else if (type == 5)
        {
            this.reference = reference;
        }
        
            
    }

    Event() 
    {
        this.t = -1;
        this.type = -1;
        this.reference = null;
    }

    @Override
    public int compareTo(Event arg0) {
        // TODO Auto-generated method stub
        if ( (this.type-arg0.type == 0)&&(this.type==1) )
        {
            Customer temp = (Customer)this.reference;
            Customer temp2 = (Customer)arg0.reference;
            return -(temp.queue_id - temp2.queue_id);
        }
        else
            return (this.type - arg0.type);
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer s = new StringBuffer();
        s.append("{");
        s.append(Integer.toString(t));
        s.append(",");
        s.append(Integer.toString(type));
        s.append(",");
        s.append(reference.toString());
        s.append("}");
        return s.toString();
    }

    
}

class Customer implements Comparable<Customer>
{
    //@param id - Customers id
    //@param arr_t - Arrival time of the customer
    // @param numb - Orders of the customer 
    int id;
    int arr_t; // Arriving time.
    int numb; // Number of Burgers ordered.
    int ser_beg_t; // time when service starts
    int ser_end_t; // time when service ends
    int queue_id;
    int semi_order = 0;
    int Beg_T = 0;
    
    Customer(int id,int t,int numb)
    {
        this.id = id;
        this.arr_t = t;
        this.numb = numb;
        this.ser_beg_t = -1;
        this.ser_end_t = -1;
    }
    @Override
    public int compareTo(Customer arg0) {
        // TODO Auto-generated method stub
        return (this.ser_beg_t - arg0.ser_beg_t) == 0?-(this.queue_id - arg0.queue_id):this.ser_beg_t - arg0.ser_beg_t;
    }
    
    public String toString()
    {
//      return "Customer: " +Integer.toString(id)+" Arrival_Time: "+Integer.toString(arr_t)+" Order: "+Integer.toString(numb)+" Semi_order: "+Integer.toString(semi_order)+" ser_beg_t: "+Integer.toString(ser_beg_t)+" ser_end_t: "+Integer.toString(ser_end_t);
        return Integer.toString(id)+"  "+ Integer.toString(semi_order);
    }
}


@SuppressWarnings("rawtypes")
class Heap<T extends Comparable<T> > implements Comparable<Heap<T>>
{
    ArrayList<T> A; // give information of queue indexes also.
    int size;
    
    Heap()
    {
        A = new ArrayList<>();
        A.add(null);
        this.size = 0;
    }
    
    Heap(T data)
    {
        A = new ArrayList<>();
        A.add(null);
        A.add(data);
        this.size = 1; // A also has [0,1]
    }
    
    T FindMin()
    {
        if(size == 0)
            return null;
        return A.get(1);
    }
    
    int insert(T data)
    {
        A.add(data);
        int i = size+1;
        while( i!=1 )
        {
            if( A.get(i/2).compareTo( A.get(i) ) > 0 )
            {
                T temp = A.get(i);
                A.set(i, A.get(i/2));
                A.set(i/2, temp);
            }
            else
                break;
            i/=2;
        }
        size +=1;
        return i;
    }
    
    void percDown(int i,T x)
    {
        if( 2*i > size )// No child hence leaf
            A.set(i, x);
        else if ( 2*i == size ) // Only one child
        {
            if( x.compareTo(A.get(2*i))>0 )// x is greater than the only child
            {
                A.set(i,A.get(2*i));
                A.set(2*i, x);
            }
            else
            {
                A.set(i, x);
            }
            
        }
        else
        {
            int j = 2*i;
            if( A.get(2*i).compareTo(A.get(2*i+1)) > 0 )
            {
                j = 2*i+1;
            }
            else
                j = 2*i;
            if( x.compareTo(A.get(j)) > 0 )
            {
                A.set(i, A.get(j));
                A.set(j, null);
                percDown(j, x);
            }
            else
                A.set(i, x);
        }
    }

    T DeleteMin()
    {
        if( size == 0 )
            return null;
        T res = A.get(1);
        T last = A.get(size);
        A.remove(size);
        size-=1;
        if(size==0)
            return res;
        A.set(1, null);
        percDown(1,last);
        return res;
    }
    
    public String toString()
    {
        return A.toString();
    }

    public int compareTo(Heap temp) {
        // TODO Auto-generated method stub
        return this.size - temp.size;
    }
    
}

// Unless asked to simulate till t or forced to simulate till t we dont simulate.
// Unless one event is processed no successive event due to that event will be generated.
// All events are time sorted and in set.
// All customers waiting are present in queues.
// All burgers/orders waiting to be cooked are in a queue which is Sorted inversely in terms of queue number.
// Simulation ends when event heap is empty.


public class MMBurgers implements MMBurgersInterface {
    int K;
    int M;
    int T = 0;
    Queue_Heap heap_of_queues;
    ABTree< Customer > BST;
    ArrayList<LinkedList<Customer>> queue_list;
    
    Heap<Tuple<Integer,Event>> Scheduler = new Heap<>();
    Heap<Customer> griddle_wait_queue = new Heap<>();
//  LinkedList<Tuple<Integer,Integer>> griddle_cooking_queue = new LinkedList<>(0);
    int patties_on_griddle = 0;
    int griddle_wait_queue_size = 0;
    int tot_arr = 0;
    float avg_wait_time = 0.0F;
    float var_wait_time = 0.0F;
    
    @SuppressWarnings({ "unchecked" })
    public Tuple<Integer, Event> perform(Event event) 
    {
//      1. Billing specialist prints an order and sends it to the chef; customer leaves the queue.
//      2. A cooked order is removed from the griddle.
//      3. A cooked semi-order has been removed.
//      4. A newly arrived customer joins a queue.
//      5. Cooked burgers are delivered to customers.
        //The order of removal for queury of type 3 and type4 has already been taken care in event of type 2.
        
        if(event.type == 1) // arrival for Orders/Griddles Queue            
        {
            // Service completion, Billing specialist has taken k time to print order and sends it to chef.
            Customer leaving_customer = (Customer)event.reference;// The leaving customer
            leaving_customer.ser_beg_t = event.t;
            LinkedList<Customer> queue_leaving_from = queue_list.get(leaving_customer.queue_id); // the queue from which he leaves
            queue_leaving_from.delete(); // delete the front
            
            // leaving_customer is now waiting for his burger and out of the queue.
//          the number of burgers he ordered are now added to queue
            heap_of_queues.BubbleUp(queue_leaving_from.heapIndex); // re-index the queues and maintain the heap.
//          System.out.println(this.heap_of_queues);
            if (queue_leaving_from.size !=0) // if the queue does not become empty, we have to schedule the dept of next customer.
            {
                Customer new_servicee = queue_leaving_from.head.data; // the new front.
                new_servicee.Beg_T = event.t;
                Scheduler.insert( new Tuple<Integer, Event>( event.t + queue_leaving_from.queue_id 
                        , new Event( event.t + queue_leaving_from.queue_id , 1, new_servicee) ) ); // Create the event of him departing
            }
            
            griddle_wait_queue.insert(leaving_customer); 
            griddle_wait_queue_size += leaving_customer.numb;
            if( griddle_wait_queue.size == 1 ) // Means it was empty before
            {
//              return new Tuple<Integer,Event>(event.t ,new Event(event.t,3,leaving_customer)); // event of adding to pattie queue 
                if (patties_on_griddle < M )
                {
                    //Event is of type 2 if Complete order has been given
                    //else the event of type 3
                    if (M-patties_on_griddle >= leaving_customer.numb)
                    {
                        griddle_wait_queue.DeleteMin();//Parchi faad di
                        griddle_wait_queue_size -= leaving_customer.numb;
//                      leaving_customer.prev_order = leaving_customer.numb;
                        patties_on_griddle+=leaving_customer.numb;
//                      System.out.println("Leaving Customer: "+leaving_customer);
                        return new Tuple<Integer,Event>(event.t+10, new Event(event.t+10, 2, new Tuple<Integer, Customer>(leaving_customer.numb, leaving_customer)) );
                    }
                    else
                    {
                        leaving_customer.semi_order = M - patties_on_griddle;
//                      leaving_customer.prev_order = M - patties_on_griddle;
                        griddle_wait_queue_size -= M - patties_on_griddle;
                        patties_on_griddle = M;
                        return new Tuple<Integer, Event>(event.t+10, new Event(event.t+10, 3, new Tuple<Integer, Customer>(leaving_customer.semi_order,leaving_customer)));
                    }

                }
            }
        }
        else if (event.type == 2)
        {
            Tuple<Integer, Customer> HH = (Tuple<Integer, Customer>)event.reference;
            Customer whose_order = HH.second;
            int prev_order = HH.first;
//          System.out.println("prev_order is "+whose_order.prev_order);
            patties_on_griddle -= prev_order;// Those burgers have been removed.
            Scheduler.insert(new Tuple<Integer, Event>(event.t+1, new Event(event.t+1,5, whose_order)));// Deliver the order to customer.
//          if(griddle_wait_queue.size!=0)// We have orders waiting from griddle.
//          {
//              
//          }
            Tuple<Integer, Event>tup = Scheduler.FindMin();
            int t_event = tup.first;
            Event eve = tup.second;
//          System.out.println("Type2 Patties on Griddle: "+patties_on_griddle);
            while (t_event == event.t && (eve.type == 2 ||eve.type == 3))
            {
                Scheduler.DeleteMin();
                
                
                HH = (Tuple<Integer, Customer>)eve.reference;
                whose_order = HH.second;
                prev_order = HH.first;
                
//              System.out.println(whose_order);
                patties_on_griddle -= prev_order;// Those burgers have been removed.
                if (eve.type == 2)
                    Scheduler.insert(new Tuple<Integer, Event>(eve.t+1, new Event(eve.t+1,5, whose_order)));
                if (Scheduler.size == 0)
                    break;
                tup = Scheduler.FindMin();
                t_event = tup.first;
                eve = tup.second;
            }
            
//          System.out.println("Type2-3 Patties on Griddle: "+patties_on_griddle);
//          System.out.println("Type2 Patties on Griddle "+patties_on_griddle);
            while (griddle_wait_queue.size!=0 && patties_on_griddle<M)
            {
                Customer cust = griddle_wait_queue.FindMin();
                if (cust.numb-cust.semi_order<=M-patties_on_griddle)
                {
                    patties_on_griddle+=cust.numb-cust.semi_order;
                    griddle_wait_queue_size -= cust.numb-cust.semi_order;
//                  cust.prev_order = cust.numb-cust.semi_order;
                    prev_order = cust.numb-cust.semi_order;
                    griddle_wait_queue.DeleteMin();
//                  System.out.println(cust);
                    Scheduler.insert(new Tuple<Integer, Event>(event.t+10, new Event(event.t+10, 2, new Tuple<Integer, Customer>(prev_order,cust))));
                }
                else if (patties_on_griddle!=M)
                {
                    cust.semi_order += M-patties_on_griddle;
                    prev_order = M-patties_on_griddle;
                    griddle_wait_queue_size -= M-patties_on_griddle;
                    patties_on_griddle = M;
//                  System.out.println("Cust is  "+cust);
                    Scheduler.insert( new Tuple<Integer, Event>(event.t+10, new Event(event.t+10, 3, new Tuple<Integer, Customer>(prev_order,cust))));
                }
            }
            return null;
        }
        else if (event.type == 3)
        {
            Tuple<Integer, Customer> HH = (Tuple<Integer, Customer>)event.reference;
            Customer whose_order = HH.second;
            int prev_order = HH.first;
//          System.out.println("Customer is :"+whose_order);
            patties_on_griddle -= prev_order;// Those burgers have been removed.
//          Scheduler.insert(new Tuple<Integer, Event>(event.t+1, new Event(event.t+1,5, whose_order)));// Deliver the order to customer.
            
            
            Tuple<Integer, Event>tup = Scheduler.FindMin();
            int t_event = tup.first;
            Event eve = tup.second;
            while (t_event == event.t && (eve.type == 2 ||eve.type == 3))
            {
                Scheduler.DeleteMin();
                
                HH = (Tuple<Integer, Customer>)eve.reference;
                whose_order = HH.second;
                prev_order = HH.first;
//              System.out.println(whose_order);
                patties_on_griddle -=prev_order;// Those burgers have been removed.
                if (eve.type == 2)
                    Scheduler.insert(new Tuple<Integer, Event>(eve.t+1, new Event(eve.t+1,5, whose_order)));
                if (Scheduler.size == 0)
                    break;
                tup = Scheduler.FindMin();
                t_event = tup.first;
                eve = tup.second;
            }
            
//          System.out.println("Type 3 patties on Griddle: "+patties_on_griddle);
            
            
            while (griddle_wait_queue.size!=0 && patties_on_griddle<M)
            {
//              System.out.println("My waiting List is "+ griddle_wait_queue);
                Customer cust = griddle_wait_queue.FindMin();
                if (cust.numb-cust.semi_order<=M-patties_on_griddle)
                {
                    patties_on_griddle+=cust.numb-cust.semi_order;
                    prev_order = cust.numb-cust.semi_order;
                    griddle_wait_queue_size -= cust.numb-cust.semi_order;
                    griddle_wait_queue.DeleteMin();
//                  System.out.println(cust);
                    Scheduler.insert(new Tuple<Integer, Event>(event.t+10, new Event(event.t+10, 2, new Tuple<Integer, Customer>(prev_order,cust))));
                }
                else if (patties_on_griddle!=M)
                {
                    cust.semi_order += M-patties_on_griddle;
                    prev_order = M-patties_on_griddle;
                    griddle_wait_queue_size -= M-patties_on_griddle;
                    patties_on_griddle = M;
//                  System.out.println("Cust is  "+cust);
                    Scheduler.insert( new Tuple<Integer, Event>(event.t+10, new Event(event.t+10, 3, new Tuple<Integer, Customer>(prev_order,cust))));
                }
            }
            return null;
        }
        else if(event.type == 4)
        {
            LinkedList<Customer> smallest_queue = heap_of_queues.getMin(); // the smallest sized queue.
            
            Customer new_customer = (Customer) event.reference;
            
            new_customer.queue_id = smallest_queue.queue_id;
            smallest_queue.insert(new_customer); // insert new_customer in queue
            heap_of_queues.BubbleDown(smallest_queue.heapIndex); // re-index the queues and maintain the heap.
//          System.out.println("TYPO 1 "+this.heap_of_queues);

//          BST.insert_key_value(new_customer.id, new_customer); // put customer in BST
            
            
            if(smallest_queue.size == 1) // This means I have to start the service of customer as he arrives, before it was 0
            {
                 // Service begins of the customer at its arrival.
                new_customer.Beg_T = event.t;
                return new Tuple<Integer, Event>( event.t + smallest_queue.queue_id 
                        , new Event( event.t + smallest_queue.queue_id , 1, new_customer) ); 
                // A new event at t+k when his service completes. Customer leaves queue at t+k.
            }           
        }
        else if (event.type == 5)
        {
//          System.out.println("Customer service completed");
            Customer cust = (Customer) event.reference;
            cust.ser_end_t = event.t;
            int wait_time = cust.ser_end_t-cust.arr_t;
            float prev_avg = avg_wait_time;
            avg_wait_time = avg_wait_time + ((wait_time)-avg_wait_time)/(tot_arr+1);
            if(tot_arr>0)
                var_wait_time = (1-1.0F/tot_arr)*var_wait_time+(tot_arr+1)*(avg_wait_time-prev_avg)*(avg_wait_time-prev_avg);
            tot_arr +=1;
            return null ;
        }
        return null;
    }
    
    public boolean isEmpty(){
        //your implementation
        return this.Scheduler.size == 0;
    } 
    
    public void setK(int k) throws IllegalNumberException{
        //your implementation
//      throw new java.lang.UnsupportedOperationException("Not implemented yet.");  
        this.K = k;
        heap_of_queues = new Queue_Heap(k); // Create Heap of k queues.
        queue_list = new ArrayList<>(); // An array of queues just to have easy access
        queue_list.add(null);
        for(int i = 1;i<=heap_of_queues.size;i++)
        {
            queue_list.add(heap_of_queues.heap.get(i));
            heap_of_queues.heap.get(i).queue_id = i;
        }
    }   
    
    public void setM(int m) throws IllegalNumberException{
        //your implementation
//      throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        this.M = m;
        
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        //your implementation
//      throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        // run all events which will happen till t.
//      System.out.println("SIMULATION TIME TILL : "+t);
//      System.out.println(this.Scheduler);
//      System.out.println("Patties on Griddle: "+this.patties_on_griddle);
//      System.out.println("Griddle Wait queue Size: "+this.griddle_wait_queue_size);
//      System.out.println("Griddle Wait Queue is : "+griddle_wait_queue);
//      System.out.println("========================================================================================================================================");
        T = t;
        if( Scheduler.size==0 )
            return;
        Tuple<Integer,Event> temp = Scheduler.FindMin();
        int t_event = temp.first;
        Event event = temp.second;
        while(t_event<=T && temp!=null)
        {
            Scheduler.DeleteMin();
            temp = perform(event);
            if( temp!=null )
                Scheduler.insert(temp); // What if An event multiple events??? -> No
            temp = Scheduler.FindMin();
            if(temp == null)
                break;
//          System.out.println(this.Scheduler);
////            System.out.println(this.heap_of_queues.heap);
//          System.out.println("Patties on Griddle: "+this.patties_on_griddle);
//          System.out.println("Griddle Wait queue Size: "+this.griddle_wait_queue_size);
//          System.out.println("Griddle Wait Queue is : "+griddle_wait_queue);
//          System.out.println("Simultation time is: "+t_event);
//          System.out.println("========================================================================================================================================");
            t_event = temp.first;
            event = temp.second;
        }
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
//      throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        Customer cust = new Customer(id,t,numb);
        if(BST == null)
            BST = new ABTree<>(id,cust);
        else if (this.BST.findData(BST.root,id) != null)
        {
            throw new IllegalNumberException("This guy already came he cannot come again during this simulation");
        }
        else{
            this.BST.insert_key_value(id, cust);
        }
//      this.advanceTime(t);
        Scheduler.insert( new Tuple<>(t, new Event(t,4,cust)) );
//      System.out.println(this.heap_of_queues);
        this.advanceTime(t);
//      System.out.println(this.heap_of_queues);
    } 

    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation
//      throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(t<T)
        {
            throw new IllegalNumberException("Asking state in past");
        }
        this.advanceTime(t);
        if (this.BST.findNode(BST.root,id)==null)
        {
            return 0;
        }
        Customer customer = this.BST.findData(BST.root,id);
        if( customer.ser_beg_t ==-1) 
            return customer.queue_id;
        else if( customer.ser_end_t ==-1 )
            return this.K+1;
        else
            return this.K + 2;
    } 

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation
        if (this.T>t)
        {
            throw new IllegalNumberException("Cannot go back in time");
        }
        this.advanceTime(t);
        return this.patties_on_griddle;
    } 

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation
        if (this.T>t)
        {
            throw new IllegalNumberException("Cannot go back in time");
        }
        this.advanceTime(t);
        return this.griddle_wait_queue_size;
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation
        if(BST.findNode(BST.root,id)==null)
        {
            throw new IllegalNumberException("This customer has never visited my restraunt");
        }
        Customer cust = BST.findData(BST.root,id);
//      System.out.println("End time:"+ Integer.toString(cust.ser_end_t)+"Arr time:"+ Integer.toString(cust.arr_t));
        return (cust.ser_end_t - cust.arr_t);
    } 

    public float avgWaitTime(){
        //your implementation
//      float wait_time=0.0F;
//      for (Map.Entry<Integer,Customer> key_val :BST.entrySet())
//      {
////            System.out.println(key_val.getValue()+" "+key_val.getValue().ser_end_t+" "+key_val.getValue().arr_t);
//          wait_time+= key_val.getValue().ser_end_t - key_val.getValue().arr_t;
//      }
//      return wait_time/BST.size();
        return avg_wait_time;
    }
    
    public float varWaitTime()
    {
        return var_wait_time;
    }
    public String toString()
    {
        return "Heap of queues        "+heap_of_queues.toString()+"      \n\n\n BST:      "+BST.toString()+"    \n\n\n Scheduler    "+Scheduler.toString()+'\n';
    }
}
