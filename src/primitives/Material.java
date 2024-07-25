package primitives;

//====== the class Material is for representing the object material type =====//

public class Material {
    // the Diffuse light factor of the object material type( מקדם הנחתה דפיוזי של סוג החומר של האובייקט) //
    public Double3 kD = new Double3(0, 0, 0);

    // the specular light factor of the object material type( מקדם הנחתה ספקולרי של סוג החומר של האובייקט) //
    public Double3 kS = new Double3(0, 0, 0);

    // the shininess factor of the object material type//
    public int nShininess = 0;
    /**
	 * kR is the reflective coefficient of the material.
	 */
	public Double3 kR = Double3.ZERO;
	/**
	 * kT is the transparent coefficient of the material.
	 */
	public Double3 kT = Double3.ZERO;
	/**
	 * nShininess is the shininess of the material.
	 */

    /**
     * set KD function - the diffuse light factor
     *
     * @param kD light factor (Double3)
     * @return
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set KD function - the diffuse light factor
     *
     * @param kD light factor (double)
     * @return
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * set kS function - the specular light factor
     *
     * @param kS light factor (Double3)
     * @return
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }


    /**
     * set kS function the specular light factor
     *
     * @param kS light factor (double)
     * @return
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**
	 * Sets the kR value.
	 *
	 * @param  kR  the new kR value
	 * @return     the updated Material object
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * Sets the kR value.
	 *
	 * @param  kR  the new kR value
	 * @return     the updated Material object
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}
	/**
	 * Sets the kT value.
	 *
	 * @param  kT  the new kD value
	 * @return     the updated Material object
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Sets the kT value.
	 *
	 * @param  kT  the new kD value
	 * @return     the updated Material object
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}

    /**
     * Set the shininess factor of the material
     *
     * @param nShininess shininess factor of the material (int)
     * @return this (Material)
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    /**
	 * Sets the shininess of the material.
	 *
	 * @param  nShininess  the new shininess value
	 * @return             the updated Material object
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
}