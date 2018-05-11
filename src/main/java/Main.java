import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import recommender.Recommender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File f = new File(".//data");

            try {


                //Creating data model
                DataModel datamodel = new FileDataModel(f); //data
                BufferedReader br = new BufferedReader(new FileReader(f));
                String s = br.readLine();
                while (s != null) {
                    System.out.println(s);
                    s = br.readLine();
                }


                //Creating UserSimilarity object.
                UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);

                //Creating UserNeighbourHHood object.
                UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(-1, usersimilarity, datamodel);

                //Create UserRecomender
                UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);

                List<RecommendedItem> recommendations = recommender.recommend(5, 1);

                System.out.println(recommendations.size());
                for (RecommendedItem recommendation : recommendations) {
                    System.out.println(recommendation);
                }

            } catch (Exception e) {
            }

    }
}
