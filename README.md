# CryptoMonitor - Monitor of cryptocurrency exchanges

CryptoMonitor is a configrable monitor of cryptocurrencies that inspects prices across the exchanges for arbitrage opportunitues.

CryptoMon is still in pre-alpha and under active development. It is currently working in polling mode with BITTREX, GDAX and POLONIEX.

Project Structure
--------------

- **cryptomon** > The main package root for the SpringBoot app and condig classes
- |-- **messaging** > Nessaging classes for websocket delivery to the front end
- |-- **model** > Common model objects
- |-- **service/arb** > Arbitrage service classes 
- |-- **service/exchange** > Base exchange service classes
- |-- **service/exchange/bitfinex** > Exchange service implementations for Bitfinex
- |-- **service/exchange/bittrex** > Exchange service implementations for Bittrex
- |-- **service/exchange/gdax** > Exchange service implementations for Gdax
- |-- **service/exchange/poloniex** > Exchange service implementations for Poloniex
- |-- **service/exchange/yobit** > Exchange service implementations for Yobit
- |-- **service/liquidity** > Common orderbook management classes
- |-- **service/streaming** > Base classes for exchange streaming updates
- |-- **service/streaming/wamp** > WAMP implementation for exchange streaming updates
- |-- **service/tickstore** > DAO classes for capturing raw data

Getting started with Development
--------------

Clone the source code by doing `git clone git@github.com:glynbach/CryptoMonitor.git`.
The eclipse plugin is included in the gradle config, to set up for eclipse type `.\gradlew eclipse` in the root directory of your cloned source.

Getting started with running the application
--------------

Note: The application will be able to populate market data from your configuration entries without any further credentials as market daya APIs for the exchanges are public.
For unlocking trading you will need a secret and API key from each of the exchanges you want to trade on. This is done on the each exchange by filling in the required details
and enabling 2-factor authentication. The secret is your private key so keep this somewhere safe.

To supply the api and secret to the application you will need to save an encrypted version of them to your file system and configure their locations in the application.yaml properties.
To save an encrypted file with those contents run the following (Use the same *AnyPwd* for each):

`java com.kieral.cryptomon.service.util.EncryptionUtils *AnyPwd* *yourApISecret* *yourSecretFilelocation*` 

`java com.kieral.cryptomon.service.util.EncryptionUtils *AnyPwd* *yourApiKey* *yourApiKeyFilelocation*`

(ApiPassphrase is used by Gdax only)
 
`java com.kieral.cryptomon.service.util.EncryptionUtils *AnyPwd* *yourApiPassphrase* *yourApiPassphraseFilelocation*`

Ensure configurations are set up correctly (see below) and then run the application with:

`java com.kieral.cryptomon.CryptoMonApp`
  
Configuring the application
--------------

Configuration is in yaml format and the following properties can be set:

- server.port > The port you want the webapp to run on 
- server.ssl > ssl settings if you are running on https
- logging > some logging options in addition to setting log level in logback.xml 
- logging.requestResponseFilters > whitelabel and blacklabel filters for response logging 
- spring.datasource > datasource settings for the ticstore service
- orderbook > significant amount settings for querying top of the book - if amounts at the top of the book are less than these settings then the levels will be accumulated until the threshold is reached 
- execution > execution settings
- common.polling-interval > common polling interval settings in millis - used to ensure the exchanges are polled at a synchronized time
**for each exchange**
- exchange > the exchange name
- enabled > if false then will not be included when application is run
- push-api > the root API URL
- snapshot-api > the public API URL for market data requests
- trading-api > the secure trading API URL for market activity
- subscription-mode > POLLING or STREAMING
- max-levels > maximum orderbook levels required
- max-trans-per-second > exchange limitation on number of requests per second if exists
- skip-hearbeats > streaming only option to skip heartbeats in sequence number validation
- snapshot-baseline > streaming only option to require a snapshot to baseline the stream
- snapshot-baseline-Sequence > streaming only option to require a sequence number form the snapshot to baseline the stream
- api-key-loc > location of encrypted file containing the api key
- api-secret-loc > location of encrypted file containing the api secret
- api-passphrase-loc > location of encrypted file containing the api passphrase (if exists - omit this property of the exchange does not require one)
- currency-pairs.pair > (repeated) the pair name as expected by the exchange API e.g. "BTC_LTC"
- currency-pairs.price-scale > scale of the prices on the exchange (usually 8)  
- currency-pairs.trading-fee > trading fee percentage for the exchange (e.g. 0.25)
- currency-pairs.desired-base-balance > the minimum balance in the base currency (e.g. LTC) that you wish to re-balance back. Arb opportunities with a low profitability will execute regardless if they rebalance to this threshold
- currency-pairs.desired-quoted-balance > the minimum balance in the quoted currency (e.g. BTC) that you wish to re-balance back. Arb opportunities with a low profitability will execute regardless if they rebalance to this threshold

Outstanding tasks
--------------

- BitFinex security module to be completed
- Yobit security module to be completed
- WAMP implementation to be re-enabled when the dependency conflicts are resolved
- GDAX streaming to be implemented
  

 


