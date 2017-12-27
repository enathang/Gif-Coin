public class Hash {

	byte[] data;

	Hash(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return this.data;
	}

	public boolean isValid() {
		if (this.data[0] != 0 ||this.data[1] != 0 || this.data[2] != 0) {
			return false;
		}

		return true;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < data.length; i++){
			long num = Byte.toUnsignedInt(data[i]);
			str += String.format("%x", num);
			//str = str + " " + num;
		}
		return str;
	}

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
