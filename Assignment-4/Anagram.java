import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class HashMap
{
	int M = 24971;
	ArrayList<String>[]hash_table;
	public static long hash(String s)
    {
        long hash = 5381;
        for (int i = 0;i<s.length();i++) 
        {
        	int character = (int)s.charAt(i);
            hash = ((hash << 5) + hash) + character;
        }
        return hash;
    }
	@SuppressWarnings("unchecked")
	public HashMap()
	{
		hash_table =new ArrayList [this.M];
	}
	@SuppressWarnings("static-access")
	public void insert(String s)
	{
		int val =(int) Math.floorMod(this.hash(s),M);;
		if (hash_table[val]==null)
		{
			hash_table[val] = new ArrayList<>();
		}
		hash_table[val].add(s);
	}
	@SuppressWarnings("static-access")
	public boolean contains(String s)
	{
		int val = (int) Math.floorMod(this.hash(s),M);
		if (this.hash_table[val] == null)
		{
			return false;
		}
		for(int i = 0;i<this.hash_table[val].size();i++)
		{
			if (this.hash_table[val].get(i).compareTo(s)==0)
			{
				return true;
			}
		}
		return false;
	}
}


class HashTable
{
	public ArrayList<ArrayList<String>>[] hash_table;
	public int Prime ;
	public long R;
	@SuppressWarnings("unchecked")
	public HashTable(int Prime)
	{
		hash_table = new ArrayList[Prime];
		this.Prime = Prime;
		this.R = 1779033703;
	}
	
	public int hash(String s)
	{
		int count[] = new int[37];
		for(int j = 0;j<s.length();j++)
		{
			count[this.atoi(s.charAt(j))]+=1;
		}
		long hash = 1L;
		for(int i = 0;i<37;i++)
		{
			hash*=Math.floorMod(this.R+2*count[i]*(i+1), this.Prime);
			hash = Math.floorMod(hash, this.Prime);
		}
		return (int)hash;
	}
	public int atoi(char temp)
	{
		if( Character.isDigit(temp) )
		{
			return (int)temp - (int)'0';
		}
		else if(Character.isLowerCase(temp))
		{
			return (int)temp - (int)'a'+10;
		}
		else
		{
			return 36;
		}
	}
	public void insert(String s)
	{
		int index = hash(s)%this.Prime;
		if (this.hash_table[index]==null)
		{
			this.hash_table[index] = new ArrayList<>();
		}
		if(this.hash_table[index].size()==0)
		{
			ArrayList<String>ans = new ArrayList<>();
			ans.add(s);
			this.hash_table[index].add(ans);
			return ;
		}
		boolean present = false;
		for(int i = 0;i<this.hash_table[index].size();i++ )
		{
			if (this.hash_table[index].get(i).get(0).length()!= s.length())
				continue;
			String we_get = this.hash_table[index].get(i).get(0);
			boolean got_it = true;
			int [] count1 = new int[37];
			int [] count2 = new int [37];
			for(int j = 0;j<s.length();j++)
			{
				count1[this.atoi(s.charAt(j))]+=1;
				count2[this.atoi(we_get.charAt(j))]+=1;
			}
			for(int j = 0;j<count1.length;j++)
			{
				if (count1[j]!=count2[j])
				{
					got_it = false;
					break;
				}
			}
			if (got_it)
			{
				present = true;
				this.hash_table[index].get(i).add(s);
			}
		}
		if (!present)
		{
			ArrayList<String>ans = new ArrayList<String>();
			ans.add(s);
			this.hash_table[index].add(ans);
		}
	}
	public int isPresent(String s)
	{
		int index = hash(s)%this.Prime;
		if (this.hash_table[index]==null)
		{
			return 0;
		}
		if(this.hash_table[index].size()==0)
		{
			return 0;
		}
		for(int i = 0;i<this.hash_table[index].size();i++ )
		{
			if (this.hash_table[index].get(i).get(0).length()!= s.length())
				continue;
			String we_get = this.hash_table[index].get(i).get(0);
			boolean got_it = true;
			int [] count1 = new int[37];
			int [] count2 = new int [37];
			for(int j = 0;j<s.length();j++)
			{
				count1[this.atoi(s.charAt(j))]+=1;
				count2[this.atoi(we_get.charAt(j))]+=1;
			}
			for(int j = 0;j<count1.length;j++)
			{
				if (count1[j]!=count2[j])
				{
					got_it = false;
					break;
				}
			}
			if (got_it)
			{
				return this.hash_table[index].get(i).size();
			}
		}
		return 0;

	}
	public ArrayList<String> anagrams(String s)
	{
		int index = hash(s)%this.Prime;
		if (this.hash_table[index]==null)
		{
			return null;
		}
		if(this.hash_table[index].size()==0)
		{
			return null;
		}
		for(int i = 0;i<this.hash_table[index].size();i++ )
		{
			if (this.hash_table[index].get(i).get(0).length()!= s.length())
				continue;
			String we_get = this.hash_table[index].get(i).get(0);
			boolean got_it = true;
			int [] count1 = new int[37];
			int [] count2 = new int [37];
			for(int j = 0;j<s.length();j++)
			{
				count1[this.atoi(s.charAt(j))]+=1;
				count2[this.atoi(we_get.charAt(j))]+=1;
			}
			for(int j = 0;j<count1.length;j++)
			{
				if (count1[j]!=count2[j])
				{
					got_it = false;
					break;
				}
			}
			if (got_it)
			{
				return this.hash_table[index].get(i);
			}
		}
		return null;

	}
}
public class Anagram
{
	
	public static String ThreeString(String s1, String s2, String s3)
	{
		String ans = "";
		if (s1.length()!=0)
		{
			ans+=s1;
		}
		if (ans.length()!=0 &&s2.length()!=0)
		{
			ans+=" "+s2;
		}
		else if (s2.length()!=0)
		{
			ans+=s2;
		}
		if (ans.length()!=0 && s3.length()!=0)
		{
			ans+=" "+s3;
		}
		else
		{
			ans+=s3;
		}
		return ans;
	}
	
	
	public static void generate_rec_strings(ArrayList<String>result,String s1, String s2, String s3, String s, int index, HashTable vocab , HashMap hash_table)
	{
		if (index == s.length())
		{
			if(s1.length()<=2)
				return;
			if(s2.length()<=2 && s1.length()!=s.length())
				return;
			if(s3.length()<=2 && s1.length()+s2.length()!=s.length())
				return;
//			System.out.println(s1+" "+s2+" "+s3);
			String ans = "";
			if (s1.length()!=0)
			{
				ans+=s1;
			}
			if (ans.length()!=0 &&s2.length()!=0)
			{
				ans+=" "+s2;
			}
			else if (s2.length()!=0)
			{
				ans+=s2;
			}
			if (ans.length()!=0 && s3.length()!=0)
			{
				ans+=" "+s3;
			}
			else
			{
				ans+=s3;
			}
			
			if (hash_table.contains(ans))
			{
				return;
			}
			
			
//			int count = 1;
			ArrayList<String>List1 = new ArrayList<>();
			ArrayList<String>List2 = new ArrayList<>();
			ArrayList<String>List3 = new ArrayList<>();
			
			if(s1.length()!=0)
			{
				if(s1.length()<=2)
					return;
				List1 = vocab.anagrams(s1);
				if (List1==null)
					return;
//				int sub_count  = 0;
//				sub_count = vocab.isPresent(s1);
//				count = count*sub_count;
			}
		// agra, s1 = ag s2 = ra, s1 = ga , s2 =ar 	
			if(s2.length()!=0)
			{
				if(s2.length()<=2)
					return;
//				int sub_count  = 0;
//				sub_count = vocab.isPresent(s2);
//				count = count*sub_count;
				List2 = vocab.anagrams(s2);
				if (List2==null)
					return;
			}
			
			if(s3.length()!=0)
			{
				if(s3.length()<=2)
					return;
//				int sub_count  = 0;
//				sub_count = vocab.isPresent(s3);
//				count = count*sub_count;
				List3 = vocab.anagrams(s3);
				if (List3==null)
					return;
			}
//			System.out.println(List1+"\n"+List2+"\n"+List3+"\n================");
			
			if (List1==null||List1.size() ==0)
			{
				List1 = new ArrayList<>();
				List1.add("");
			}
			if (List2==null||List2.size()==0)
			{
				List2 = new ArrayList<>();
				List2.add("");
			}
			if (List3 == null||List3.size()==0)
			{
				List3 = new ArrayList<>();
				List3.add("");
			}
			for(int i = 0;i<List1.size();i++)
			{
				for(int j = 0;j<List2.size();j++)
				{
					for(int k  = 0;k<List3.size();k++)
					{
						String ins = ThreeString(List1.get(i),List2.get(j),List3.get(k));
						if (i==0 &&j==0 &&k==0 && hash_table.contains(ins))
						{
							return;
						}
						if (ins.length()!=0)
						{
							result.add(ins);
							hash_table.insert(ins);
						}
					}
				}
			}
			
			
			return;
		}
		generate_rec_strings(result, s1+s.charAt(index), s2, s3, s, index+1, vocab, hash_table);
		generate_rec_strings(result, s1, s2+s.charAt(index), s3, s, index+1, vocab, hash_table);
		generate_rec_strings(result, s1, s2, s3+s.charAt(index), s, index+1, vocab, hash_table);
	}
	
	public static void Generator(String s, HashTable vocab)
	{
		HashMap hash_table = new HashMap();
		String s1= "";
		String s2 = "";
		String s3 = "";
		ArrayList<String> result = new ArrayList<>();
		generate_rec_strings(result,s1,s2,s3,s,0,vocab,hash_table);
		Collections.sort(result);
		for(int i = 0;i<result.size();i++)
		{
			System.out.println(result.get(i));
		}
	}

	
//	public HashTable vocab;
	public static void main(String []args)
	{
		HashTable hashtable = new HashTable(24971);
		FileInputStream file;
		Scanner sc;
		try {
//			file  = new FileInputStream("vocabulary.txt");
			file = new FileInputStream(args[0]);
			sc = new Scanner(file);
			int K = sc.nextInt();
			int count = 0;
			int maxChain = 0;
			for(int i=0;i<K;i++)
			{
				String word = sc.next();
				hashtable.insert(word);
			}
			for(int i = 0;i<hashtable.hash_table.length;i++)
			{
				if (hashtable.hash_table[i]!=null)
				{
					maxChain = Math.max(maxChain, hashtable.hash_table[i].size());
					count+=hashtable.hash_table[i].size()-1;
//					System.out.println(hashtable.hash_table[i]);
				}
			}
//			System.out.println("The length of vocab is:"+K);
//			System.out.println("The number of collisions is:"+count);
//			System.out.println("Max chain length is:"+maxChain);
			sc.close();
			
			file = new FileInputStream(args[1]);
//			file = new FileInputStream("input.txt");
			sc = new Scanner(file);
			K = sc.nextInt();
			for(int i =0;i<K;i++)
			{
				String word = sc.next();
//				System.out.println(word);
//				List<String> result = new ArrayList<>();
				Generator(word,hashtable);
//				Collections.sort(result);
//				List<String>new_result = new ArrayList<>();
				System.out.println(-1);
			}
			sc.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}