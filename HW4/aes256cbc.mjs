#!/usr/bin/env node

if (process.argv.length !== 6) {
    console.error('Expected exactly four arguments! The first is either -e or -d (for encrypt and decrypt, respectively). The second is either a message to encrypt or a ciphertext to decrypt. The third is the key. The fourth is an initialization vector.');
    process.exit(1);
}
if (process.argv[2] !== '-e' && process.argv[2] !== '-d') {
    console.error('Expected either "-e" or "-d" as first argument!');
    process.exit(1);
}

import { createCipheriv, createDecipheriv } from 'crypto';

const algorithm = 'aes-256-cbc';
const mode = process.argv[2];
const message = process.argv[3];
const key = process.argv[4];
const iv = process.argv[5];

switch (mode) {
    case '-e':
        console.log(encrypt(message, key, iv));
        break;
    case '-d':
        console.log(decrypt(message, key, iv));
        break;
}

function encrypt(message, key, iv) {
    const cipher = createCipheriv(algorithm, key, iv);
    let encrypted = cipher.update(message, 'utf8', 'hex');
    encrypted += cipher.final('hex');
    return encrypted.toUpperCase();
}

function decrypt(ciphertext, key, iv) {
    const decipher = createDecipheriv(algorithm, key, iv);
    let decrypted = decipher.update(ciphertext, 'hex', 'utf8');
    decrypted += decipher.final('utf8');
    return decrypted;
}
