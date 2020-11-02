# testTask

# bugs on positive cases:
1. invalid perimeter value for double values: "0.1,0.1,0.1"
	expected [0.3] but found [0.30000000000000004]
2. unable to use + * - signs  as separator: status 500 with response    

		"message": "Dangling meta character '*' near index 0\n*\n^",
2. unable to use $ signs  as separator: status 500 with response      
	
		"exception": "com.natera.test.triangle.exception.UnprocessableDataException",
   		"message": "Cannot process input",


# bugs on negative cases:
1.  the sum of the two sides of the triangle must always be greater than the third side. examples:

		{"2,1,1", "Cannot process input"}, //should not be created
		{"1,2,1", "Cannot process input"},  //should not be created
                {"1,1,2", "Cannot process input"}, //should not be created
2. triangles with 4 sides should not be created:
				
		{"3,5,2,2", "Cannot process input"},
3. triangle with 0 side should not be created:
	
		{"0,0,0", "Cannot process input"},

