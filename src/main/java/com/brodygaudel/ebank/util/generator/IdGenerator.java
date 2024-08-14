

package com.brodygaudel.ebank.util.generator;

public interface IdGenerator {
    /**
     * This method generates the (unique) identifier of the bank account.
     * the identifier of the bank accounts represents its bic code (or account details)
     *
     * @return unique id for a bank account
     */
    String autoGenerate();
}
