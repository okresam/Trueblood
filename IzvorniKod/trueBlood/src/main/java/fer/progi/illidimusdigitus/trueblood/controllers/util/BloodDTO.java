package fer.progi.illidimusdigitus.trueblood.controllers.util;

import fer.progi.illidimusdigitus.trueblood.model.util.BloodType;

public class BloodDTO {

    public String name;

    public int upperbound;

    public int lowerbound;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BloodDTO(String name, int upperbound, int lowerbound) {
		super();
		this.name = name;
		this.upperbound = upperbound;
		this.lowerbound = lowerbound;
	}

	public int getUpperbound() {
        return upperbound;
    }

    public void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    public int getLowerbound() {
        return lowerbound;
    }

    public void setLowerbound(int lowerbound) {
        this.lowerbound = lowerbound;
    }


}
