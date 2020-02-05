package com.example.testingbot.migration;

import com.example.testingbot.domain.ImageEntity;
import com.example.testingbot.repository.ImageRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageMigration {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageMigration(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void migration() {
        List<ImageEntity> images = Lists.newArrayList(
                new ImageEntity(null, "https://drive.google.com/open?id=1KL7-0Zpoe2p9N8n_Rq5WPgwe4N81QA5k", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1Iblf477kClKydswcL5tqRhF58x5gzriU", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1mCs6C8seGtQ7qLprn-y6rxIDIKVtcGic", null),
                new ImageEntity(null, "https://drive.google.com/open?id=13gnIJrzSQ0ppkD2qwOZvT3gy90MPucPE", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1mci5OsoYuc1JKRaDu9_dCElFKz0Vbe3o", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1ShdpaygGZyS3STC_oZ5anp5W9AqRa5-g", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1GP5M8UEhiQ14GID0DyZwIz0Cbeuv8WwA", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1n-3ZidggBw9LPPOzJJkdHVcFHps44fEm", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1uL3ZzMDdBS3MtuTQTS5ZRNgN-tZalSV2", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1HXA_5DRX6hPPbZIIyiFGBXqQklHAXvY3", null),
                new ImageEntity(null, "https://drive.google.com/open?id=16KXzOEAgeJUSO6LesG2mjcvP_E3CG2ZF", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1yLLkFxSYdhtJDcfOMPZI1Pj-Lq21tDDP", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1vT81SrRRwk_vCcKaHZzxyD1q62P9C5_F", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1nNCAmmVQXsqmk4IlSerDmApU0Axf8mXJ", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1NfDvk9T500M_vBaARWSV5WXB2lQCvnOx", null),
                new ImageEntity(null, "https://drive.google.com/open?id=17NfZAW8Q284jrbLdQwkdsjFI3tOF49_g", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1x0C9PrpT9lRND8WmrFUM8ioa4TMRHRzT", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1tRB-izr_JWl6KoMA2GlhRVALumrFqNUU", null),
                new ImageEntity(null, "https://drive.google.com/open?id=18exrtGBI1bptVKvdiKQhwQ2QWAVmT8f1", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1ZLNZetuei9Iga-JXlrOR2pA3sbHmPb1k", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1pENl0bcymzG_LByFNRTH21pRYO_3NQyT", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1Z19S6GuGH1mFakGpBadSSBEWTkcJCl7w", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1CFhuC2LaYxn_D0yXGxhf3UEIiIOT_6Xa", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1_5nbs82Gut3Kj-Yugtzv9mur4gWzDmSR", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1DBrOovMjPIPdN-lyD1kN75QagSVSD9kt", null),
                new ImageEntity(null, "https://drive.google.com/open?id=17l9iWCnsdlwElZPMQHVt55JPd_leLS_F", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1bCjJaPkEt6xvUU66dhczBJZCGrLiOrmG", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1bCjJaPkEt6xvUU66dhczBJZCGrLiOrmG", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1cCjBYeH_aYZrioTH8qVd5UmI59VHX0bk", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1fTqeX27GIPB1taz9JfM9OeLOm8p1wAsY", null),
                new ImageEntity(null, "https://drive.google.com/open?id=16V8Fp4B2MqTpf13zXEUPlB3mpJ2XFjgA", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1NXSNy7MyWiFsiRQz4htnWVgW5ROGl40B", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1UUhqQdKFtLFpNce5DrK6hbMbMJc6WvgF", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1cMODW4Weka4z7GO5_e67D_wOu5O00vzE", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1FPdqjrsYmbU9TKyXrylmNiGIprBHcwvZ", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1E3yjZMap3A8b9RCUD0j4ZJzX3FYH2b7a", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1exnIBgvrz0B4FpTZBOdaHKaZ4kY0H1cz", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1RpS8enIz-OlujTqhR0irW92Vkbkx7Jw_", null),
                new ImageEntity(null, "https://drive.google.com/open?id=11KFIkmYHawX4ACuXI7QWhB1L3z480PHA", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1KY_BFg-4Uunn5H9RD9OfhpifoLuklnS9", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1np9mRRfUJM5TElNhhbiN5YEkgaZYjgSS", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1MHY_DtOU6Qr0zzS4MoHLgV72PI6wIirG", null),
                new ImageEntity(null, "https://drive.google.com/open?id=11pJkpM7DTruPQWmnLy1stDQ1zHIWPy_g", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1lm4orgTCdnJgQ4h749IG6xSw0caF3Ev0", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1R4ykg9szWf1RxSiNWXpUqka_OzRtsSHb", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1NBwKXvJWKdOTbZb4fnfG5AQxgneqrBdL", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1M6zI-hagrOUf0fO2MD_mMjjT_pOseiD0", null),
                new ImageEntity(null, "https://drive.google.com/open?id=15ShkX2J4GL6bRXYuwe7DAnsciHM1ybkq", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1oCx02_Ea9d1e1U-GSyilror94to_IahD", null),
                new ImageEntity(null, "https://drive.google.com/open?id=14ZtxKTOaFPMNctNst5idWy5OMZXtq-21", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1vuPLCWa25cvbkAIHSsfk_mnK7_-RPF6A", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1bgGdx6BMPcRFmVw2aKRxYgBdNI-gFdI9", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1W7Kub1A6Nusivjla5IxySvWfkapX13oK", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1i_n8aSKnDnKEUYTeMGMFiI4aGswBM3bG", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1LO-gL5zSgvsjDo2EGD9OPD-EYY3pht7o", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1_iH0hadoKM4-TSsx4xMzuiUgsn-M2Vpu", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1YonmY-Fgo_V3xzr8IQnTvIq5Z-Mwp9n-", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1_fLe5LhAqPyM3x0DQub4FqCeZK1Yg0FL", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1oa-MYvMkpUlDQqEfT6MFXRp8BSKFqtl_", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1U5wNWSsIpsAtcL9e3uEbn9lIPZ-EvQIW", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1iOiZc3SW0tBu-64J9gIp5c4xydFZUIvX", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1zkqqNm0I6MQuLD97yR0Heh0kjG1DJnxn", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1EWPVC_gZO0JilQvVN10yBfAxU2P7a_lG", null),
                new ImageEntity(null, "https://drive.google.com/open?id=130sAOLJFd0fdkcX5d-6gC5AI1Rd_-A4z", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1HK8jYSnfMKU0FOKPFRAaody3zUmJ99sj", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1rNQXdimkKlZY2I5caMgOfWdbV3wTfeoq", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1rRo6m7RBMCzYXmOrQya7oMFBGut2nP-8", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1SmystKE-yM14Xdz_OcguUn1G39y0dMPc", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1e3gkGerNbvlUdN6mMXN2elIE8xJJcXUG", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1J-Ojia39chRM89d73jNbSGbdLEWx2lU1", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1C3LsWAmKTGgXKj2kpjQ7tVVFxA5kIydL", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1vSxzfYQsw8dYXSUDaC81EkxSVCugL3EB", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1F7RmbFowSovUU49Rh2xOdXRZxKRjKVaJ", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1wIKKQi9PkOnb4uA0uSfyYxmarQpiJ_LD", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1wQN2nkF5QO47sF7vw5x6C83N-Ga1_hLb", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1jc8g8cDBkoftfLlQCPK0Y-UBOmP1VpEJ", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1AuV8UUcn5EXqsIM11HMT7RToEGBf85mp", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1-sRwa1V-d6Np7svRxsJJHZi6Ah7L0XeD", null),
                new ImageEntity(null, "https://drive.google.com/open?id=19hhvwoxkYBvGkYgpIYEu4ThLGSOOwpXb", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1KH1yemQ-lN8ZT0t147HSLWe-B17G63Ol", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1yvJmJ27tWzEWNqSuds747zb7wpFBfuZt", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1HQrhjS9Q58zXf-03QkIU_fOpbKeV15s8", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1fBrl7CH8DfBhkb4WG8zuSso3xX_-2JBE", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1sn5t0qpmkV79j79BIQWHzWB0sgYb9clh", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1VM4PWCVIBvV3clk84NNg9PZmC6WCsLrx", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1eyygrLDbMA1OyFqQNlN3h3KNZGS-KRAw", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1WVZ8Ee2nPEqH-8w_j5L5UzIG08FNxx5R", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1xKs56rXJJ4rxyPXXg9LRm5n6FiiB9yRF", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1N8Zm7IWlhpIuAqSwhNL1xZCMC_ZSntip", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1bUgnZwBY-NZ1f-PacfkBucHdtJLJ8hBy", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1VzTKiFgYrrOoMR1rdsS0mjDx78v4k_1n", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1j_bH_38s_PKeKks6WnyFugXtj6rd6vOd", null),
                new ImageEntity(null, "https://drive.google.com/open?id=15un6ltMzcuHaVP2sXPKFUBJyPyljBuSF", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1xDugME-GBaSyZA0aD2YLoQ3igpRkiVkr", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1ByjkRo350t4mRfAkWHWNKa1IHOl1s__K", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1yl4kr2K4hz7oLyC4Q6TUbMK8qy-i9jS5", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1NVccou92uzTOyDxRGw1zMYexdP2tInxn", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1k-bRlT10u_TlfF3ZaBkdkKhtZsZTmIC5", null),
                new ImageEntity(null, "https://drive.google.com/open?id=1g1ONz5fkFVMjhaOYCr6sv6f4Vx71c1xD", null),
                new ImageEntity(null, "https://drive.google.com/open?id=149oz0lpZ01QCGCRLbR7ELhelUccak2zc", null),
                new ImageEntity(null, "https://drive.google.com/open?id=14yLW5MGfoKwugaYrrbhLfZ3ntVB72kqu", null)
        );
        images.forEach(imageRepository::save);
    }
}
