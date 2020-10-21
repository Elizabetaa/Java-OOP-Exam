package viceCity.models.guns;

public class Pistol extends BaseGun {
    private static final int BULLETS_PER_BARREL = 10;
    private static final int TOTAL_BULLETS = 100;

    public Pistol(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }


    @Override
    public int fire() {
        if (this.getBulletsPerBarrel() >= 1) {
            setBulletsPerBarrel(this.getBulletsPerBarrel() - 1);
            if (this.getBulletsPerBarrel() == 0) {
                if (this.getTotalBullets() >= 10) {
                    this.setTotalBullets(this.getTotalBullets() - BULLETS_PER_BARREL);
                    this.setBulletsPerBarrel(BULLETS_PER_BARREL);
                }
            }
            return 1;
        } else {
            if (this.getTotalBullets() >= 10) {
                this.setTotalBullets(this.getTotalBullets() - BULLETS_PER_BARREL);
                this.setBulletsPerBarrel(BULLETS_PER_BARREL);
                setBulletsPerBarrel(this.getBulletsPerBarrel() - 1);
                return 1;
            }
        }
        return 0;
    }
}
