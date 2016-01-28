package training.mansour.beautifullibya.Services;

import android.widget.Toast;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Mansour on 28/01/2016.
 */
public class MyService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "JOB START", Toast.LENGTH_LONG).show();
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
