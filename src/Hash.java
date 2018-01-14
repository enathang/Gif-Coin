public class Hash {
	byte[] data;

	/**
     * Creates a new hash
     *
     * @param  data the byte array of data
     */
	Hash(byte[] data) {
		this.data = data;
	}

	/**
     * Returns the data of the hash
     *
     * @return  the data
     */
	public byte[] getData() {
		return this.data;
	}

	/**
     * Checks if the hash is valid (ie starts with 000)
     *
     * @return whether or not the hash starts wirh 000
     */
	public boolean isValid() {
		if (this.data[0] != 0 || this.data[1] != 0 || this.data[2] != 0) {
			return false;
		}

		return true;
	}

	/**
     * Converts the hash to a readable string and returns it
     *
     * @return the string form of the hash
     */
	public String toString() {
		String str = "";
		for (int i = 0; i < data.length; i++){
			long num = Byte.toUnsignedInt(data[i]);
			str += String.format("%x", num);
		}
		return str;
	}

	/**
     * Checks to see if the object passed is equal to this hash
     *
     * @return whether or not the hashes are equal
     */
	public boolean equals(Object other) {
		if (!(other instanceof Hash)) {
			return false;
		}

		if (!(this.data.equals((Hash)other))) {
			return false;
		}

		return true;
	}

}
