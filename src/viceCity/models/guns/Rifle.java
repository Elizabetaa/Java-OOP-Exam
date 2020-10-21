package viceCity.models.guns;

public class Rifle extends BaseGun {
    private static final int BULLETS_PER_BARREL = 50;
    private static final int TOTAL_BULLETS = 500;


    public Rifle(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }

    @Override
    public int fire() {
        if (this.getBulletsPerBarrel() >= 5) {
            setBulletsPerBarrel(this.getBulletsPerBarrel() - 5);
            if (this.getBulletsPerBarrel() == 0) {
                if (this.getTotalBullets() >= 50) {
                    this.setTotalBullets(this.getTotalBullets() - BULLETS_PER_BARREL);
                    this.setBulletsPerBarrel(BULLETS_PER_BARREL);
                }
            }
            return 5;
        } else {
            if (this.getTotalBullets() >= 50) {
                this.setTotalBullets(this.getTotalBullets() - BULLETS_PER_BARREL);
                this.setBulletsPerBarrel(BULLETS_PER_BARREL);
                setBulletsPerBarrel(this.getBulletsPerBarrel() - 5);
                return 5;
            }
        }
        return 0;
    }
}
