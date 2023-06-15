import math

def encrypt(msg, e, n):
	hex_msg = msg.encode("utf-8").hex()
	dec = int(hex_msg, base=16)
	cipher_text = pow(dec, e, n)
	return cipher_text

def decrypt(cipher, d, n):
	dec_cipher = pow(cipher, d, n)
	h = hex(dec_cipher).removeprefix('0x')
	b = bytes.fromhex(h).decode()
	return b

def getModInverse(a, m):
	if math.gcd(a, m) != 1:
		return None
	u1, u2, u3 = 1, 0, a
	v1, v2, v3 = 0, 1, m

	while v3 != 0:
		q = u3 // v3
		v1, v2, v3, u1, u2, u3 = (u1 - q * v1), (u2 - q * v2), (u3 - q * v3), v1, v2, v3
	return u1 % m

def main():
	p = 100392089237316158323570985008687907853269981005640569039457584007913129640081
	q = 90392089237316158323570985008687907853269981005640569039457584007913129640041
	e = 65537
	message = "Scaramouche, Scaramouche, will you do the Fandango? 💃🏽"
	print("Message: " + str(message))

	# compute n
	n = p * q
	print("n = " + str(n))

	# Compute phi(n)
	phi = (p - 1) * (q - 1)

	# Compute modular inverse of e
	d = getModInverse(e, phi)
	print("d = " + str(d))

	cipher_text = encrypt(message, e, n)
	print("Encrypted = " + str(cipher_text))

	# Decrypt ciphertext
	decrypted = decrypt(cipher_text, d, n)
	print("Decrypted = " + str(decrypted))


if __name__ == "__main__":
	main()
