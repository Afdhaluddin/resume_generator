package com.resumegen.dto.response;

public class UsageResponse {
    private boolean canGenerate;
    private int remaining;
    private int used;
    private int limit;

    public UsageResponse(boolean canGenerate, int remaining, int used, int limit) {
        this.canGenerate = canGenerate;
        this.remaining = remaining;
        this.used = used;
        this.limit = limit;
    }

    public boolean isCanGenerate() { return canGenerate; }
    public void setCanGenerate(boolean canGenerate) { this.canGenerate = canGenerate; }
    public int getRemaining() { return remaining; }
    public void setRemaining(int remaining) { this.remaining = remaining; }
    public int getUsed() { return used; }
    public void setUsed(int used) { this.used = used; }
    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }
}
