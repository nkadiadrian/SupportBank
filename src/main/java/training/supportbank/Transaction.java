package training.supportbank;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Transaction {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private Date date;

    @SerializedName("fromAccount")
    private String from;
    @SerializedName("toAccount")
    private String to;
    private String narrative;
    private double amount;

    public Transaction(String record) {
        List<String> fields = Arrays.asList(record.split(","));
        try {
            date = DATE_FORMAT.parse(fields.get(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.from = fields.get(1);
        this.to = fields.get(2);
        this.narrative = fields.get(3);
        this.amount = Double.parseDouble(fields.get(4));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction: " +
                "date: " + date +
                ", from: '" + from + '\'' +
                ", to: '" + to + '\'' +
                ", narrative: '" + narrative + '\'' +
                ", amount: " + amount;
    }
}
