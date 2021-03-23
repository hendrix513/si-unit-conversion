package com.example.restservice;


import java.util.*;


class SIUnit {
       String name;
       float multiplicationFactor;

       SIUnit(String name, float muliplicationFactor) {
       	   this.name = name;
       	   this.multiplicationFactor = muliplicationFactor;
       }
}

class SIUnitsConversion {
	private static Map<String, SIUnit> nameToSI;
	private static Map<String, String> symbolToName;
	private static Set<String> siUnitNames;
	private String units;
	private String multiplicationFactor;

	static {
		nameToSI = new HashMap<>();
		symbolToName = new HashMap<>();
		siUnitNames = new HashSet<>();

		nameToSI.put("minute", new SIUnit("s", 60f));
		nameToSI.put("hour", new SIUnit("s", 3600f));
		nameToSI.put("day", new SIUnit("s", 86400f));
		nameToSI.put("degree",  new SIUnit("rad", (float) Math.PI/180f));
		nameToSI.put("arcminute", new SIUnit("rad", (float) Math.PI/10800f));
		nameToSI.put("arcsecond", new SIUnit("rad", (float) Math.PI/648000f));
		nameToSI.put("hectare", new SIUnit("m^2", 10000f));
		nameToSI.put("litre", new SIUnit("m^3", .001f));
		nameToSI.put("tonne", new SIUnit("kg", 1000f));

		symbolToName.put("min", "minute");
		symbolToName.put("h", "hour");
		symbolToName.put("d", "day");
		symbolToName.put("°", "degree");
		symbolToName.put("'", "arcminute");
		symbolToName.put("\"", "arcsecond");
		symbolToName.put("ha", "hectare");
		symbolToName.put("L", "litre");
		symbolToName.put("t", "tonne");

		siUnitNames.add("s");
		siUnitNames.add("rad");
		siUnitNames.add("m^2");
		siUnitNames.add("m^3");
		siUnitNames.add("kg");
	}

	public String getunit_name() {
		return units;
	}

	public String getmultiplication_factor() {
		return multiplicationFactor;
	}

	private static float eval(char c, float val1, float val2) {
		if (c == '/') return val1 / val2;
		if (c == '*') return val1 * val2;
		throw new RuntimeException("Invalid operator");
	}

	SIUnitsConversion(String units) {
		TreeMap<Character, Integer> precedence = new TreeMap<>();
		precedence.put('(', 0);
		precedence.put(')', 0);
		precedence.put('*', 2);
		precedence.put('/', 2);

		Stack<Character> ops  = new Stack<>();
		Stack<Float> vals = new Stack<>();

		StringBuilder tmp = new StringBuilder();
		StringBuilder conv = new StringBuilder();
		for (int i=0; i < units.length(); i++) {
			char c = units.charAt(i);

			if (precedence.containsKey(c)) {
				if (tmp.length() > 0) {
					String tmpStr = tmp.toString();
					SIUnit p;
					if (siUnitNames.contains(tmpStr)) {
						p = new SIUnit(tmpStr, 1f);
					} else if (symbolToName.containsKey(tmpStr)) {
						p = nameToSI.get(symbolToName.get(tmpStr));
					} else {
						p = nameToSI.get(tmpStr);
					}

					conv.append(p.name);
					vals.push(p.multiplicationFactor);
				}
				conv.append(c);

				tmp = new StringBuilder();
				while (true) {
					if (ops.isEmpty() || c == '(' || (precedence.get(c) > precedence.get(ops.peek()))) {
						ops.push(c);
						break;
					}

					char op = ops.pop();

					if (op == '(') {
						assert c == ')';
						break;
					}

					else {
						float val2 = vals.pop();
						float val1 = vals.pop();
						vals.push(eval(op, val1, val2));
					}
				}
			} else {
				tmp.append(c);
			}
		}

		while (!ops.isEmpty()) {
			char op = ops.pop();
			float val2 = vals.pop();
			float val1 = vals.pop();
			vals.push(eval(op, val1, val2));
		}

		Float val = vals.pop();
		assert vals.isEmpty();
		assert ops.isEmpty();

		multiplicationFactor = String.format("%.14f", val);
		this.units = conv.toString();
	}
}