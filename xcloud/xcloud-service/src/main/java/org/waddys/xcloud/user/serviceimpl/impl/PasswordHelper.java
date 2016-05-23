package org.waddys.xcloud.user.serviceimpl.impl;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.user.bo.User;

@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    // @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    // @Value("${password.hashIterations}")
    private int hashIterations = 2;

    public void setRandomNumberGenerator(
            RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(User user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }

    public Boolean equalTo(User user, String oldPassword) {

        String oldPasswordEncrypt = new SimpleHash(algorithmName, oldPassword,
                ByteSource.Util.bytes(user.getUsername() + user.getSalt()),
                hashIterations).toHex();
        if (oldPasswordEncrypt.equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }

    }
}
