#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

/*
Encrypts a string by 1st adding 100 to each characters ASCII value
then multiplying that by 4. This way each character's ASCII value is
comprised of 3 digits. Then add each triple of digits into a new string.
To decode, divide each group of 3 digits by 4 then subtract by 100 
*/
char* encrypt(char *s) {
    size_t size = strlen(s);                // compliant with STR32-C
    if (size <=1 || size >= 100){           // compliant with ARR32-C
        return -1;
    }
    char *ptr = (char *)calloc((size+1)*3, sizeof(char));
    for (size_t i = 0; i <= size; i++) {    // compliant with FLP30-C
        int encodedChar = (s[i] + 100)*4;        
        sprintf(ptr+(i*3), "%ld", encodedChar);
    }
    return ptr;
}

int main() {
    char raw_message[] = "Encrypt this message.";
    printf("1. The raw message is:\t\t%s\n", raw_message);
    char* array = encrypt(raw_message);
    printf("2. The encrypted message is:\t%s\n", array);
    return 0;
}
