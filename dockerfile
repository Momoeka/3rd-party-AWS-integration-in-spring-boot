FROM java:latest
COPY . .
RUN javac mausam.java
EXPOSE 8080
CMD ["java", "mausam"]