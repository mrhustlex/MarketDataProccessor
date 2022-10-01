# MarketDataProccessor
Java for generating random data using raw excel from Nasdaq

Assumptions
All calls to the onMessage method are made by one thread.
The MarketDataProcessor receives messages from some source via the onMessage call back method. The incoming rate is unknown.
Each second only have 100 or less symbols updates received.
If there is an exception that more than 100 unique symbols updates received within 1-second time window, only the first 100 unique symbols should be handled.
"latest market data" means the data I published were the most recent market data I received at that moment.
May publishes the same price if the first price update in previous time-window, and the first price update in current time-window are the same.


This Java project is a random data generator. Not for real production use. Please feel free to modify code for further usage
