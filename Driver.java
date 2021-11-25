import java.util.*;

class ContainerType {
	
}

class Refrigerate extends ContainerType {
	float power;
	Refrigerate(float val) {
		power = val;
	}
}

class Toxic extends ContainerType {
	float risk;
	Toxic(float val) {
		risk = val;
	}
}

class Liquid extends ContainerType {
	float sway;
	Liquid(float val) {
		sway = val;
	}
}

class Regular extends ContainerType {
	
}

class Weak extends ContainerType {
	boolean isWeak;
}

class Container {
	int id;
	int type;
	int weight;
	int height;
	ContainerType ct;
	
	void assignType(float val) {
		switch(type) {
		case 1:
			ct = new Regular();
			break;
		case 2:
			ct = new Refrigerate(val);
			break;
		case 3:
			ct = new Liquid(val);
			break;
		case 4:
			ct = new Toxic(val);
			break;
		case 5:
			ct = new Weak();
			break;
		}
	}
}


class Ship {
	int stackNo;
	int stackSize;
	int maxWeight;
	float riskTolerance;
	float powerOut;
	float swayTolerance;
	int maxHeight;
}

class HeightComparator implements Comparator<Container> {
	@Override
	public int compare(Container c1, Container c2)
    {
        if (c1.height == c2.height)
            return 0;
        else if (c1.height < c2.height)
            return 1;
        else
            return -1;
    }
}

class Crane {
	ArrayList<ArrayList<Integer>> arrange = new ArrayList<ArrayList<Integer>>();
	
	boolean stack(Ship shipObj, ArrayList<Container> containerArray) {
		for(int i=0; i<shipObj.stackNo; i++) {
			arrange.add(new ArrayList<Integer>());
		}
		Collections.sort(containerArray, new HeightComparator());

		int totalWeight = 0;
		float totalRisk = 0f;
		float totalPower = 0f;
		float totalSway = 0f;
		ArrayList<Integer> totalHeight = new ArrayList<>();
		for(int i=0; i<shipObj.stackNo; i++) {
			totalHeight.add(0);
		}
		ArrayList<Container> weakContainer = new ArrayList<>();

		int n = containerArray.size();
		boolean weak = false;
		boolean direction = true;
		int s = 0;
		
		if(n > shipObj.stackNo* shipObj.stackSize) {
			return false;
		}
		for(int i=0; i<n; i++) {
			Container c = containerArray.get(i);
			
			if(c.height > shipObj.maxHeight) {
				return false;
			}
			
			int t = c.type;
			switch(t) {
			case 1:
				Regular a = (Regular) c.ct;
				break;
			case 2:
				Refrigerate r = (Refrigerate) c.ct;
				totalPower = totalPower + r.power;
				if(totalPower > shipObj.powerOut) {
					return false;
				}
				break;
			case 3:
				Liquid l = (Liquid) c.ct;
				totalSway = totalSway + l.sway;
				if(totalSway > shipObj.swayTolerance) {
					return false;
				}
				break;
			case 4:
				Toxic to = (Toxic) c.ct;
				totalRisk = totalRisk + to.risk;
				if(totalRisk > shipObj.riskTolerance) {
					return false;
				}
				break;
			case 5:
				Weak w = (Weak) c.ct;
				w.isWeak = true;
				weak = true;
				weakContainer.add(c);
				break;
			}
			
			if(weak) {
				weak = false;
				continue;
			}
			
			totalWeight = totalWeight + c.weight;
			if(totalWeight > shipObj.maxWeight) {
				return false;
			}
			
			int h;
			h = totalHeight.get(s) + c.height;
			if(h > shipObj.maxHeight) {
				return false;
			}
			else {
				totalHeight.set(s, h);
			}
			
			arrange.get(s).add(c.id);
			
			if(direction) {
				if(s == shipObj.stackNo-1) {
					direction = false;
				}
				else {
					s = s+1;
				}
			}
			else {
				if(s == 0) {
					direction = true;
				}
				else {
					s = s-1;
				}
			}
			
		}
	
		HashSet<Integer> mp = new HashSet<>();
		for(Container w:weakContainer) {
			s = 0;
			totalWeight = totalWeight + w.weight;
			if(totalWeight > shipObj.maxWeight) {
				return false;
			}
	
			while(mp.contains(s)) {
				s = s+1;
				if(s == shipObj.stackNo) {
					return false;
				}
			}
			
			int h = totalHeight.get(s) + w.height;
			while(h > shipObj.maxHeight) {
				s = s+1;
				h = totalHeight.get(s) + w.height;
				if(s == shipObj.stackNo) {
					return false;
				}
			}
			totalHeight.set(s, h);
			mp.add(s);
			arrange.get(s).add(w.id);
		}
		
		return true;
	}
}


public class Driver {

	public static void main(String[] args) {
			Ship shipObj = new Ship();
			
			Scanner in = new Scanner(System.in);
			System.out.println("Ship Details:-");
			
			System.out.println("Enter number of stacks: ");
			shipObj.stackNo = in.nextInt();
			System.out.println("Enter capacity of stack: ");
			shipObj.stackSize = in.nextInt();
			System.out.println("Enter maximum weight load: ");
			shipObj.maxWeight = in.nextInt();
			System.out.println("Enter maximum risk tolerance: ");
			shipObj.riskTolerance = in.nextFloat();
			System.out.println("Enter maximum Output power: ");
			shipObj.powerOut = in.nextFloat();
			System.out.println("Enter maximum sway tolerance: ");
			shipObj.swayTolerance = in.nextFloat();
			System.out.println("Enter maximum height of a stack: ");
			shipObj.maxHeight = in.nextInt();
			
			System.out.println("Ship Details stored!");
			System.out.println("");
			
			System.out.println("Enter mumber of containers: ");
			int n = in.nextInt();
			
			ArrayList<Container> containerArray = new ArrayList<>();
			
			System.out.println("Please enter Container details in the format as mentioned below:- ");
			System.out.println("Each line contains details of 1 unique container.");
			System.out.println("The 1st entry will be the container id, followed by its type, weight, & height. After that enter the next parameter as per the type of the container..");
			for(int i=0; i<n; i++) {
				Container c = new Container();
				float val = 0f;
				c.id = in.nextInt();
				int t = in.nextInt();
				c.type = t;
				c.weight = in.nextInt();
				c.height = in.nextInt();
				
				if(t!=1 && t!=5) {
					val = in.nextFloat();
				}
				c.assignType(val);
				
				containerArray.add(c);
				
			}
			
			Crane crane = new Crane();
			boolean ans = crane.stack(shipObj, containerArray);
			
			System.out.println("");
			if(ans == false) {
				System.out.println("Arrangement Not possible :(");
			}
			else {
				System.out.println("Yes!It's Possible!");
				System.out.println(crane.arrange);
			}

	}
	
}
