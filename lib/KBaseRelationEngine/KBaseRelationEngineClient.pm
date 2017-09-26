package KBaseRelationEngine::KBaseRelationEngineClient;

use JSON::RPC::Client;
use POSIX;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
my $get_time = sub { time, 0 };
eval {
    require Time::HiRes;
    $get_time = sub { Time::HiRes::gettimeofday() };
};

use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

KBaseRelationEngine::KBaseRelationEngineClient

=head1 DESCRIPTION


A KBase module: KBaseRelationEngine


=cut

sub new
{
    my($class, $url, @args) = @_;
    

    my $self = {
	client => KBaseRelationEngine::KBaseRelationEngineClient::RpcClient->new,
	url => $url,
	headers => [],
    };

    chomp($self->{hostname} = `hostname`);
    $self->{hostname} ||= 'unknown-host';

    #
    # Set up for propagating KBRPC_TAG and KBRPC_METADATA environment variables through
    # to invoked services. If these values are not set, we create a new tag
    # and a metadata field with basic information about the invoking script.
    #
    if ($ENV{KBRPC_TAG})
    {
	$self->{kbrpc_tag} = $ENV{KBRPC_TAG};
    }
    else
    {
	my ($t, $us) = &$get_time();
	$us = sprintf("%06d", $us);
	my $ts = strftime("%Y-%m-%dT%H:%M:%S.${us}Z", gmtime $t);
	$self->{kbrpc_tag} = "C:$0:$self->{hostname}:$$:$ts";
    }
    push(@{$self->{headers}}, 'Kbrpc-Tag', $self->{kbrpc_tag});

    if ($ENV{KBRPC_METADATA})
    {
	$self->{kbrpc_metadata} = $ENV{KBRPC_METADATA};
	push(@{$self->{headers}}, 'Kbrpc-Metadata', $self->{kbrpc_metadata});
    }

    if ($ENV{KBRPC_ERROR_DEST})
    {
	$self->{kbrpc_error_dest} = $ENV{KBRPC_ERROR_DEST};
	push(@{$self->{headers}}, 'Kbrpc-Errordest', $self->{kbrpc_error_dest});
    }

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my %arg_hash2 = @args;
	if (exists $arg_hash2{"token"}) {
	    $self->{token} = $arg_hash2{"token"};
	} elsif (exists $arg_hash2{"user_id"}) {
	    my $token = Bio::KBase::AuthToken->new(@args);
	    if (!$token->error_message) {
	        $self->{token} = $token->token;
	    }
	}
	
	if (exists $self->{token})
	{
	    $self->{client}->{token} = $self->{token};
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 getFeatureSequences

  $return = $obj->getFeatureSequences($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetFeatureSequencesParams
$return is a reference to a list where each element is a KBaseRelationEngine.FeatureSequence
GetFeatureSequencesParams is a reference to a hash where the following keys are defined:
	taxonomy_guid has a value which is a string
	ortholog_guid has a value which is a string
	goterm_guid has a value which is a string
FeatureSequence is a reference to a hash where the following keys are defined:
	taxonomy_guid has a value which is a string
	feature_guid has a value which is a string
	proteinSequence has a value which is a string
	nucleotideSequence has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetFeatureSequencesParams
$return is a reference to a list where each element is a KBaseRelationEngine.FeatureSequence
GetFeatureSequencesParams is a reference to a hash where the following keys are defined:
	taxonomy_guid has a value which is a string
	ortholog_guid has a value which is a string
	goterm_guid has a value which is a string
FeatureSequence is a reference to a hash where the following keys are defined:
	taxonomy_guid has a value which is a string
	feature_guid has a value which is a string
	proteinSequence has a value which is a string
	nucleotideSequence has a value which is a string


=end text

=item Description



=back

=cut

 sub getFeatureSequences
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getFeatureSequences (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getFeatureSequences:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getFeatureSequences');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getFeatureSequences",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getFeatureSequences',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getFeatureSequences",
					    status_line => $self->{client}->status_line,
					    method_name => 'getFeatureSequences',
				       );
    }
}
 


=head2 getCompendiumDescriptors

  $return = $obj->getCompendiumDescriptors($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetCompendiumDescriptorsParams
$return is a reference to a list where each element is a KBaseRelationEngine.CompendiumDescriptor
GetCompendiumDescriptorsParams is a reference to a hash where the following keys are defined:
	taxonomy_guid has a value which is a string
	data_type has a value which is a string
CompendiumDescriptor is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	data_type has a value which is a string
	taxonomy_guid has a value which is a string
	ws_ndarray_id has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetCompendiumDescriptorsParams
$return is a reference to a list where each element is a KBaseRelationEngine.CompendiumDescriptor
GetCompendiumDescriptorsParams is a reference to a hash where the following keys are defined:
	taxonomy_guid has a value which is a string
	data_type has a value which is a string
CompendiumDescriptor is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	data_type has a value which is a string
	taxonomy_guid has a value which is a string
	ws_ndarray_id has a value which is a string


=end text

=item Description



=back

=cut

 sub getCompendiumDescriptors
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getCompendiumDescriptors (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getCompendiumDescriptors:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getCompendiumDescriptors');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getCompendiumDescriptors",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getCompendiumDescriptors',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getCompendiumDescriptors",
					    status_line => $self->{client}->status_line,
					    method_name => 'getCompendiumDescriptors',
				       );
    }
}
 


=head2 storeKEAppDescriptor

  $return = $obj->storeKEAppDescriptor($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.StoreKEAppDescriptorParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreKEAppDescriptorParams is a reference to a hash where the following keys are defined:
	app has a value which is a KBaseRelationEngine.KEAppDescriptor
KEAppDescriptor is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	version has a value which is a string
	last_run_epoch has a value which is an int
	nodes_created has a value which is an int
	relations_created has a value which is an int
	properties_set has a value which is an int
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.StoreKEAppDescriptorParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreKEAppDescriptorParams is a reference to a hash where the following keys are defined:
	app has a value which is a KBaseRelationEngine.KEAppDescriptor
KEAppDescriptor is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	version has a value which is a string
	last_run_epoch has a value which is an int
	nodes_created has a value which is an int
	relations_created has a value which is an int
	properties_set has a value which is an int
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub storeKEAppDescriptor
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function storeKEAppDescriptor (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to storeKEAppDescriptor:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'storeKEAppDescriptor');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.storeKEAppDescriptor",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'storeKEAppDescriptor',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method storeKEAppDescriptor",
					    status_line => $self->{client}->status_line,
					    method_name => 'storeKEAppDescriptor',
				       );
    }
}
 


=head2 cleanKEAppResults

  $obj->cleanKEAppResults($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.CleanKEAppResultsParams
CleanKEAppResultsParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.CleanKEAppResultsParams
CleanKEAppResultsParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string


=end text

=item Description



=back

=cut

 sub cleanKEAppResults
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function cleanKEAppResults (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to cleanKEAppResults:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'cleanKEAppResults');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.cleanKEAppResults",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'cleanKEAppResults',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return;
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method cleanKEAppResults",
					    status_line => $self->{client}->status_line,
					    method_name => 'cleanKEAppResults',
				       );
    }
}
 


=head2 getKEAppDescriptor

  $return = $obj->getKEAppDescriptor($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetKEAppDescriptorParams
$return is a KBaseRelationEngine.KEAppDescriptor
GetKEAppDescriptorParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppDescriptor is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	version has a value which is a string
	last_run_epoch has a value which is an int
	nodes_created has a value which is an int
	relations_created has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetKEAppDescriptorParams
$return is a KBaseRelationEngine.KEAppDescriptor
GetKEAppDescriptorParams is a reference to a hash where the following keys are defined:
	app_guid has a value which is a string
KEAppDescriptor is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	version has a value which is a string
	last_run_epoch has a value which is an int
	nodes_created has a value which is an int
	relations_created has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub getKEAppDescriptor
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getKEAppDescriptor (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getKEAppDescriptor:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getKEAppDescriptor');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getKEAppDescriptor",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getKEAppDescriptor',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getKEAppDescriptor",
					    status_line => $self->{client}->status_line,
					    method_name => 'getKEAppDescriptor',
				       );
    }
}
 


=head2 storeBiclusters

  $return = $obj->storeBiclusters($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.StoreBiclustersParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreBiclustersParams is a reference to a hash where the following keys are defined:
	biclusters has a value which is a reference to a list where each element is a KBaseRelationEngine.Bicluster
Bicluster is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	compendium_guid has a value which is a string
	taxonomy_guid has a value which is a string
	feature_guids has a value which is a reference to a list where each element is a string
	condition_guids has a value which is a reference to a list where each element is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.StoreBiclustersParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreBiclustersParams is a reference to a hash where the following keys are defined:
	biclusters has a value which is a reference to a list where each element is a KBaseRelationEngine.Bicluster
Bicluster is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	compendium_guid has a value which is a string
	taxonomy_guid has a value which is a string
	feature_guids has a value which is a reference to a list where each element is a string
	condition_guids has a value which is a reference to a list where each element is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub storeBiclusters
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function storeBiclusters (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to storeBiclusters:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'storeBiclusters');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.storeBiclusters",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'storeBiclusters',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method storeBiclusters",
					    status_line => $self->{client}->status_line,
					    method_name => 'storeBiclusters',
				       );
    }
}
 


=head2 getBiclusters

  $return = $obj->getBiclusters($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetBiclustersParams
$return is a reference to a list where each element is a KBaseRelationEngine.Bicluster
GetBiclustersParams is a reference to a hash where the following keys are defined:
	keapp_guid has a value which is a string
	taxonomy_guid has a value which is a string
	compendium_guid has a value which is a string
Bicluster is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	compendium_guid has a value which is a string
	taxonomy_guid has a value which is a string
	feature_guids has a value which is a reference to a list where each element is a string
	condition_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetBiclustersParams
$return is a reference to a list where each element is a KBaseRelationEngine.Bicluster
GetBiclustersParams is a reference to a hash where the following keys are defined:
	keapp_guid has a value which is a string
	taxonomy_guid has a value which is a string
	compendium_guid has a value which is a string
Bicluster is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	compendium_guid has a value which is a string
	taxonomy_guid has a value which is a string
	feature_guids has a value which is a reference to a list where each element is a string
	condition_guids has a value which is a reference to a list where each element is a string


=end text

=item Description



=back

=cut

 sub getBiclusters
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getBiclusters (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getBiclusters:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getBiclusters');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getBiclusters",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getBiclusters',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getBiclusters",
					    status_line => $self->{client}->status_line,
					    method_name => 'getBiclusters',
				       );
    }
}
 


=head2 storeTermEnrichmentProfiles

  $return = $obj->storeTermEnrichmentProfiles($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.StoreTermEnrichmentProfilesParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreTermEnrichmentProfilesParams is a reference to a hash where the following keys are defined:
	profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile
TermEnrichmentProfile is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	source_gene_set_guid has a value which is a string
	source_gene_set_type has a value which is a string
	term_namespace has a value which is a string
	terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment
TermEnrichment is a reference to a hash where the following keys are defined:
	term_guid has a value which is a string
	term_name has a value which is a string
	sample_count has a value which is an int
	total_count has a value which is an int
	expected_count has a value which is an int
	p_value has a value which is a float
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.StoreTermEnrichmentProfilesParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreTermEnrichmentProfilesParams is a reference to a hash where the following keys are defined:
	profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile
TermEnrichmentProfile is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	source_gene_set_guid has a value which is a string
	source_gene_set_type has a value which is a string
	term_namespace has a value which is a string
	terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment
TermEnrichment is a reference to a hash where the following keys are defined:
	term_guid has a value which is a string
	term_name has a value which is a string
	sample_count has a value which is an int
	total_count has a value which is an int
	expected_count has a value which is an int
	p_value has a value which is a float
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub storeTermEnrichmentProfiles
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function storeTermEnrichmentProfiles (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to storeTermEnrichmentProfiles:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'storeTermEnrichmentProfiles');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.storeTermEnrichmentProfiles",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'storeTermEnrichmentProfiles',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method storeTermEnrichmentProfiles",
					    status_line => $self->{client}->status_line,
					    method_name => 'storeTermEnrichmentProfiles',
				       );
    }
}
 


=head2 getWSFeatureTermEnrichmentProfiles

  $return = $obj->getWSFeatureTermEnrichmentProfiles($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetWSFeatureTermEnrichmentProfilesParams
$return is a KBaseRelationEngine.GetWSFeatureTermEnrichmentProfilesOutput
GetWSFeatureTermEnrichmentProfilesParams is a reference to a hash where the following keys are defined:
	ws_feature_guid has a value which is a string
	ortholog_profiles has a value which is a KBaseRelationEngine.boolean
	keapp_guids has a value which is a reference to a list where each element is a string
boolean is an int
GetWSFeatureTermEnrichmentProfilesOutput is a reference to a hash where the following keys are defined:
	feature_guid has a value which is a string
	feature_name has a value which is a string
	ref_term_guid has a value which is a string
	ref_term_name has a value which is a string
	profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile
TermEnrichmentProfile is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	source_gene_set_guid has a value which is a string
	source_gene_set_type has a value which is a string
	term_namespace has a value which is a string
	terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment
TermEnrichment is a reference to a hash where the following keys are defined:
	term_guid has a value which is a string
	term_name has a value which is a string
	sample_count has a value which is an int
	total_count has a value which is an int
	expected_count has a value which is an int
	p_value has a value which is a float

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetWSFeatureTermEnrichmentProfilesParams
$return is a KBaseRelationEngine.GetWSFeatureTermEnrichmentProfilesOutput
GetWSFeatureTermEnrichmentProfilesParams is a reference to a hash where the following keys are defined:
	ws_feature_guid has a value which is a string
	ortholog_profiles has a value which is a KBaseRelationEngine.boolean
	keapp_guids has a value which is a reference to a list where each element is a string
boolean is an int
GetWSFeatureTermEnrichmentProfilesOutput is a reference to a hash where the following keys are defined:
	feature_guid has a value which is a string
	feature_name has a value which is a string
	ref_term_guid has a value which is a string
	ref_term_name has a value which is a string
	profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile
TermEnrichmentProfile is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	source_gene_set_guid has a value which is a string
	source_gene_set_type has a value which is a string
	term_namespace has a value which is a string
	terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment
TermEnrichment is a reference to a hash where the following keys are defined:
	term_guid has a value which is a string
	term_name has a value which is a string
	sample_count has a value which is an int
	total_count has a value which is an int
	expected_count has a value which is an int
	p_value has a value which is a float


=end text

=item Description



=back

=cut

 sub getWSFeatureTermEnrichmentProfiles
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getWSFeatureTermEnrichmentProfiles (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getWSFeatureTermEnrichmentProfiles:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getWSFeatureTermEnrichmentProfiles');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getWSFeatureTermEnrichmentProfiles",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getWSFeatureTermEnrichmentProfiles',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getWSFeatureTermEnrichmentProfiles",
					    status_line => $self->{client}->status_line,
					    method_name => 'getWSFeatureTermEnrichmentProfiles',
				       );
    }
}
 


=head2 getWSFeatureTermPairs

  $return = $obj->getWSFeatureTermPairs($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetWSFeatureTermPairsParams
$return is a reference to a list where each element is a KBaseRelationEngine.WSFeatureTermPair
GetWSFeatureTermPairsParams is a reference to a hash where the following keys are defined:
	ws_genome_guid has a value which is a string
	target_keapp_guid has a value which is a string
WSFeatureTermPair is a reference to a hash where the following keys are defined:
	feature_guid has a value which is a string
	feature_name has a value which is a string
	feature_function has a value which is a string
	feature_aliases has a value which is a reference to a list where each element is a string
	with_expression has a value which is a KBaseRelationEngine.boolean
	with_fitness has a value which is a KBaseRelationEngine.boolean
	ref_term_guid has a value which is a string
	ref_term_name has a value which is a string
	target_term_guid has a value which is a string
	target_term_name has a value which is a string
boolean is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetWSFeatureTermPairsParams
$return is a reference to a list where each element is a KBaseRelationEngine.WSFeatureTermPair
GetWSFeatureTermPairsParams is a reference to a hash where the following keys are defined:
	ws_genome_guid has a value which is a string
	target_keapp_guid has a value which is a string
WSFeatureTermPair is a reference to a hash where the following keys are defined:
	feature_guid has a value which is a string
	feature_name has a value which is a string
	feature_function has a value which is a string
	feature_aliases has a value which is a reference to a list where each element is a string
	with_expression has a value which is a KBaseRelationEngine.boolean
	with_fitness has a value which is a KBaseRelationEngine.boolean
	ref_term_guid has a value which is a string
	ref_term_name has a value which is a string
	target_term_guid has a value which is a string
	target_term_name has a value which is a string
boolean is an int


=end text

=item Description



=back

=cut

 sub getWSFeatureTermPairs
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getWSFeatureTermPairs (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getWSFeatureTermPairs:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getWSFeatureTermPairs');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getWSFeatureTermPairs",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getWSFeatureTermPairs',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getWSFeatureTermPairs",
					    status_line => $self->{client}->status_line,
					    method_name => 'getWSFeatureTermPairs',
				       );
    }
}
 


=head2 getFeatureTerms

  $return = $obj->getFeatureTerms($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetFeatureTermsParams
$return is a reference to a list where each element is a KBaseRelationEngine.FeatureTerms
GetFeatureTermsParams is a reference to a hash where the following keys are defined:
	taxon_guid has a value which is a string
	term_space has a value which is a string
FeatureTerms is a reference to a hash where the following keys are defined:
	feature_guid has a value which is a string
	term_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetFeatureTermsParams
$return is a reference to a list where each element is a KBaseRelationEngine.FeatureTerms
GetFeatureTermsParams is a reference to a hash where the following keys are defined:
	taxon_guid has a value which is a string
	term_space has a value which is a string
FeatureTerms is a reference to a hash where the following keys are defined:
	feature_guid has a value which is a string
	term_guids has a value which is a reference to a list where each element is a string


=end text

=item Description



=back

=cut

 sub getFeatureTerms
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getFeatureTerms (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getFeatureTerms:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getFeatureTerms');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getFeatureTerms",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getFeatureTerms',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getFeatureTerms",
					    status_line => $self->{client}->status_line,
					    method_name => 'getFeatureTerms',
				       );
    }
}
 


=head2 getTerms

  $return = $obj->getTerms($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetTermsParams
$return is a reference to a list where each element is a KBaseRelationEngine.Term
GetTermsParams is a reference to a hash where the following keys are defined:
	term_guids has a value which is a reference to a list where each element is a string
Term is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	space has a value which is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetTermsParams
$return is a reference to a list where each element is a KBaseRelationEngine.Term
GetTermsParams is a reference to a hash where the following keys are defined:
	term_guids has a value which is a reference to a list where each element is a string
Term is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	space has a value which is a string


=end text

=item Description



=back

=cut

 sub getTerms
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getTerms (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getTerms:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getTerms');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getTerms",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getTerms',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getTerms",
					    status_line => $self->{client}->status_line,
					    method_name => 'getTerms',
				       );
    }
}
 


=head2 getOrthologGroups

  $return = $obj->getOrthologGroups($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetOrthologGroupsParams
$return is a KBaseRelationEngine.GetOrthologGroupsOutput
GetOrthologGroupsParams is a reference to a hash where the following keys are defined:
	with_term_enrichmnet_profiles has a value which is a KBaseRelationEngine.boolean
	app_guids has a value which is a reference to a list where each element is a string
boolean is an int
GetOrthologGroupsOutput is a reference to a hash where the following keys are defined:
	ortholog_group_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetOrthologGroupsParams
$return is a KBaseRelationEngine.GetOrthologGroupsOutput
GetOrthologGroupsParams is a reference to a hash where the following keys are defined:
	with_term_enrichmnet_profiles has a value which is a KBaseRelationEngine.boolean
	app_guids has a value which is a reference to a list where each element is a string
boolean is an int
GetOrthologGroupsOutput is a reference to a hash where the following keys are defined:
	ortholog_group_guids has a value which is a reference to a list where each element is a string


=end text

=item Description



=back

=cut

 sub getOrthologGroups
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getOrthologGroups (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getOrthologGroups:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getOrthologGroups');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getOrthologGroups",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getOrthologGroups',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getOrthologGroups",
					    status_line => $self->{client}->status_line,
					    method_name => 'getOrthologGroups',
				       );
    }
}
 


=head2 getOrthologTermEnrichmentProfiles

  $return = $obj->getOrthologTermEnrichmentProfiles($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.GetOrthologTermEnrichmentProfilesParams
$return is a KBaseRelationEngine.GetOrthologTermEnrichmentProfilesOutput
GetOrthologTermEnrichmentProfilesParams is a reference to a hash where the following keys are defined:
	ortholog_group_guids has a value which is a reference to a list where each element is a string
	app_guids has a value which is a reference to a list where each element is a string
GetOrthologTermEnrichmentProfilesOutput is a reference to a hash where the following keys are defined:
	ortholog2profiles has a value which is a reference to a hash where the key is a string and the value is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile
TermEnrichmentProfile is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	source_gene_set_guid has a value which is a string
	source_gene_set_type has a value which is a string
	term_namespace has a value which is a string
	terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment
TermEnrichment is a reference to a hash where the following keys are defined:
	term_guid has a value which is a string
	term_name has a value which is a string
	sample_count has a value which is an int
	total_count has a value which is an int
	expected_count has a value which is an int
	p_value has a value which is a float

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.GetOrthologTermEnrichmentProfilesParams
$return is a KBaseRelationEngine.GetOrthologTermEnrichmentProfilesOutput
GetOrthologTermEnrichmentProfilesParams is a reference to a hash where the following keys are defined:
	ortholog_group_guids has a value which is a reference to a list where each element is a string
	app_guids has a value which is a reference to a list where each element is a string
GetOrthologTermEnrichmentProfilesOutput is a reference to a hash where the following keys are defined:
	ortholog2profiles has a value which is a reference to a hash where the key is a string and the value is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile
TermEnrichmentProfile is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	keapp_guid has a value which is a string
	source_gene_set_guid has a value which is a string
	source_gene_set_type has a value which is a string
	term_namespace has a value which is a string
	terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment
TermEnrichment is a reference to a hash where the following keys are defined:
	term_guid has a value which is a string
	term_name has a value which is a string
	sample_count has a value which is an int
	total_count has a value which is an int
	expected_count has a value which is an int
	p_value has a value which is a float


=end text

=item Description



=back

=cut

 sub getOrthologTermEnrichmentProfiles
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getOrthologTermEnrichmentProfiles (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getOrthologTermEnrichmentProfiles:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getOrthologTermEnrichmentProfiles');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.getOrthologTermEnrichmentProfiles",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getOrthologTermEnrichmentProfiles',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getOrthologTermEnrichmentProfiles",
					    status_line => $self->{client}->status_line,
					    method_name => 'getOrthologTermEnrichmentProfiles',
				       );
    }
}
 


=head2 storeWSGenome

  $return = $obj->storeWSGenome($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.StoreWSGenomeParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreWSGenomeParams is a reference to a hash where the following keys are defined:
	genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
	feature_guids has a value which is a reference to a list where each element is a KBaseRelationEngine.ws_feature_guid
ws_genome_obj_ref is a string
ws_feature_guid is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.StoreWSGenomeParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreWSGenomeParams is a reference to a hash where the following keys are defined:
	genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
	feature_guids has a value which is a reference to a list where each element is a KBaseRelationEngine.ws_feature_guid
ws_genome_obj_ref is a string
ws_feature_guid is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub storeWSGenome
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function storeWSGenome (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to storeWSGenome:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'storeWSGenome');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.storeWSGenome",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'storeWSGenome',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method storeWSGenome",
					    status_line => $self->{client}->status_line,
					    method_name => 'storeWSGenome',
				       );
    }
}
 


=head2 storeRichWSGenome

  $return = $obj->storeRichWSGenome($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.StoreRichWSGenomeParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreRichWSGenomeParams is a reference to a hash where the following keys are defined:
	genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
	features has a value which is a reference to a list where each element is a KBaseRelationEngine.WSFeature
	ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string
ws_genome_obj_ref is a string
WSFeature is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	function has a value which is a string
	aliases has a value which is a reference to a list where each element is a string
	ref_term_guid has a value which is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.StoreRichWSGenomeParams
$return is a KBaseRelationEngine.GraphUpdateStat
StoreRichWSGenomeParams is a reference to a hash where the following keys are defined:
	genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
	features has a value which is a reference to a list where each element is a KBaseRelationEngine.WSFeature
	ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string
ws_genome_obj_ref is a string
WSFeature is a reference to a hash where the following keys are defined:
	guid has a value which is a string
	name has a value which is a string
	function has a value which is a string
	aliases has a value which is a reference to a list where each element is a string
	ref_term_guid has a value which is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub storeRichWSGenome
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function storeRichWSGenome (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to storeRichWSGenome:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'storeRichWSGenome');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.storeRichWSGenome",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'storeRichWSGenome',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method storeRichWSGenome",
					    status_line => $self->{client}->status_line,
					    method_name => 'storeRichWSGenome',
				       );
    }
}
 


=head2 connectWSFeatures2RefOrthologs

  $return = $obj->connectWSFeatures2RefOrthologs($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.ConnectWSFeatures2RefOrthologsParams
$return is a KBaseRelationEngine.GraphUpdateStat
ConnectWSFeatures2RefOrthologsParams is a reference to a hash where the following keys are defined:
	ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.ConnectWSFeatures2RefOrthologsParams
$return is a KBaseRelationEngine.GraphUpdateStat
ConnectWSFeatures2RefOrthologsParams is a reference to a hash where the following keys are defined:
	ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub connectWSFeatures2RefOrthologs
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function connectWSFeatures2RefOrthologs (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to connectWSFeatures2RefOrthologs:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'connectWSFeatures2RefOrthologs');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.connectWSFeatures2RefOrthologs",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'connectWSFeatures2RefOrthologs',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method connectWSFeatures2RefOrthologs",
					    status_line => $self->{client}->status_line,
					    method_name => 'connectWSFeatures2RefOrthologs',
				       );
    }
}
 


=head2 connectWSFeatures2RefOTerms

  $return = $obj->connectWSFeatures2RefOTerms($params)

=over 4

=item Parameter and return types

=begin html

<pre>
$params is a KBaseRelationEngine.ConnectWSFeatures2RefOTermsParams
$return is a KBaseRelationEngine.GraphUpdateStat
ConnectWSFeatures2RefOTermsParams is a reference to a hash where the following keys are defined:
	feature2term_list has a value which is a reference to a hash where the key is a KBaseRelationEngine.ws_feature_guid and the value is a reference to a list where each element is a KBaseRelationEngine.ref_ontology_term_guid
ws_feature_guid is a string
ref_ontology_term_guid is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int

</pre>

=end html

=begin text

$params is a KBaseRelationEngine.ConnectWSFeatures2RefOTermsParams
$return is a KBaseRelationEngine.GraphUpdateStat
ConnectWSFeatures2RefOTermsParams is a reference to a hash where the following keys are defined:
	feature2term_list has a value which is a reference to a hash where the key is a KBaseRelationEngine.ws_feature_guid and the value is a reference to a list where each element is a KBaseRelationEngine.ref_ontology_term_guid
ws_feature_guid is a string
ref_ontology_term_guid is a string
GraphUpdateStat is a reference to a hash where the following keys are defined:
	nodes_created has a value which is an int
	nodes_deleted has a value which is an int
	relationships_created has a value which is an int
	relationships_deleted has a value which is an int
	properties_set has a value which is an int


=end text

=item Description



=back

=cut

 sub connectWSFeatures2RefOTerms
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function connectWSFeatures2RefOTerms (received $n, expecting 1)");
    }
    {
	my($params) = @args;

	my @_bad_arguments;
        (ref($params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"params\" (value was \"$params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to connectWSFeatures2RefOTerms:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'connectWSFeatures2RefOTerms');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "KBaseRelationEngine.connectWSFeatures2RefOTerms",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'connectWSFeatures2RefOTerms',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method connectWSFeatures2RefOTerms",
					    status_line => $self->{client}->status_line,
					    method_name => 'connectWSFeatures2RefOTerms',
				       );
    }
}
 
  
sub status
{
    my($self, @args) = @_;
    if ((my $n = @args) != 0) {
        Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
                                   "Invalid argument count for function status (received $n, expecting 0)");
    }
    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
        method => "KBaseRelationEngine.status",
        params => \@args,
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
                           code => $result->content->{error}->{code},
                           method_name => 'status',
                           data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
                          );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method status",
                        status_line => $self->{client}->status_line,
                        method_name => 'status',
                       );
    }
}
   

sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
        method => "KBaseRelationEngine.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'connectWSFeatures2RefOTerms',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method connectWSFeatures2RefOTerms",
            status_line => $self->{client}->status_line,
            method_name => 'connectWSFeatures2RefOTerms',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for KBaseRelationEngine::KBaseRelationEngineClient\n";
    }
    if ($sMajor == 0) {
        warn "KBaseRelationEngine::KBaseRelationEngineClient version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 boolean

=over 4



=item Definition

=begin html

<pre>
an int
</pre>

=end html

=begin text

an int

=end text

=back



=head2 GraphUpdateStat

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
nodes_created has a value which is an int
nodes_deleted has a value which is an int
relationships_created has a value which is an int
relationships_deleted has a value which is an int
properties_set has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
nodes_created has a value which is an int
nodes_deleted has a value which is an int
relationships_created has a value which is an int
relationships_deleted has a value which is an int
properties_set has a value which is an int


=end text

=back



=head2 FeatureSequence

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
taxonomy_guid has a value which is a string
feature_guid has a value which is a string
proteinSequence has a value which is a string
nucleotideSequence has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
taxonomy_guid has a value which is a string
feature_guid has a value which is a string
proteinSequence has a value which is a string
nucleotideSequence has a value which is a string


=end text

=back



=head2 GetFeatureSequencesParams

=over 4



=item Description

*
One of guids should provided.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
taxonomy_guid has a value which is a string
ortholog_guid has a value which is a string
goterm_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
taxonomy_guid has a value which is a string
ortholog_guid has a value which is a string
goterm_guid has a value which is a string


=end text

=back



=head2 CompendiumDescriptor

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
data_type has a value which is a string
taxonomy_guid has a value which is a string
ws_ndarray_id has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
data_type has a value which is a string
taxonomy_guid has a value which is a string
ws_ndarray_id has a value which is a string


=end text

=back



=head2 GetCompendiumDescriptorsParams

=over 4



=item Description

*
data_type - one of ["expression","fitness"]


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
taxonomy_guid has a value which is a string
data_type has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
taxonomy_guid has a value which is a string
data_type has a value which is a string


=end text

=back



=head2 KEAppDescriptor

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
version has a value which is a string
last_run_epoch has a value which is an int
nodes_created has a value which is an int
relations_created has a value which is an int
properties_set has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
version has a value which is a string
last_run_epoch has a value which is an int
nodes_created has a value which is an int
relations_created has a value which is an int
properties_set has a value which is an int


=end text

=back



=head2 StoreKEAppDescriptorParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app has a value which is a KBaseRelationEngine.KEAppDescriptor

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app has a value which is a KBaseRelationEngine.KEAppDescriptor


=end text

=back



=head2 CleanKEAppResultsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 GetKEAppDescriptorParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
app_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
app_guid has a value which is a string


=end text

=back



=head2 Bicluster

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
guid has a value which is a string
keapp_guid has a value which is a string
compendium_guid has a value which is a string
taxonomy_guid has a value which is a string
feature_guids has a value which is a reference to a list where each element is a string
condition_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
guid has a value which is a string
keapp_guid has a value which is a string
compendium_guid has a value which is a string
taxonomy_guid has a value which is a string
feature_guids has a value which is a reference to a list where each element is a string
condition_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 StoreBiclustersParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
biclusters has a value which is a reference to a list where each element is a KBaseRelationEngine.Bicluster

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
biclusters has a value which is a reference to a list where each element is a KBaseRelationEngine.Bicluster


=end text

=back



=head2 GetBiclustersParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
keapp_guid has a value which is a string
taxonomy_guid has a value which is a string
compendium_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
keapp_guid has a value which is a string
taxonomy_guid has a value which is a string
compendium_guid has a value which is a string


=end text

=back



=head2 TermEnrichment

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
term_guid has a value which is a string
term_name has a value which is a string
sample_count has a value which is an int
total_count has a value which is an int
expected_count has a value which is an int
p_value has a value which is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
term_guid has a value which is a string
term_name has a value which is a string
sample_count has a value which is an int
total_count has a value which is an int
expected_count has a value which is an int
p_value has a value which is a float


=end text

=back



=head2 TermEnrichmentProfile

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
guid has a value which is a string
keapp_guid has a value which is a string
source_gene_set_guid has a value which is a string
source_gene_set_type has a value which is a string
term_namespace has a value which is a string
terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
guid has a value which is a string
keapp_guid has a value which is a string
source_gene_set_guid has a value which is a string
source_gene_set_type has a value which is a string
term_namespace has a value which is a string
terms has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichment


=end text

=back



=head2 StoreTermEnrichmentProfilesParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile


=end text

=back



=head2 GetWSFeatureTermEnrichmentProfilesOutput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
feature_guid has a value which is a string
feature_name has a value which is a string
ref_term_guid has a value which is a string
ref_term_name has a value which is a string
profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
feature_guid has a value which is a string
feature_name has a value which is a string
ref_term_guid has a value which is a string
ref_term_name has a value which is a string
profiles has a value which is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile


=end text

=back



=head2 GetWSFeatureTermEnrichmentProfilesParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ws_feature_guid has a value which is a string
ortholog_profiles has a value which is a KBaseRelationEngine.boolean
keapp_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ws_feature_guid has a value which is a string
ortholog_profiles has a value which is a KBaseRelationEngine.boolean
keapp_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 WSFeatureTermPair

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
feature_guid has a value which is a string
feature_name has a value which is a string
feature_function has a value which is a string
feature_aliases has a value which is a reference to a list where each element is a string
with_expression has a value which is a KBaseRelationEngine.boolean
with_fitness has a value which is a KBaseRelationEngine.boolean
ref_term_guid has a value which is a string
ref_term_name has a value which is a string
target_term_guid has a value which is a string
target_term_name has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
feature_guid has a value which is a string
feature_name has a value which is a string
feature_function has a value which is a string
feature_aliases has a value which is a reference to a list where each element is a string
with_expression has a value which is a KBaseRelationEngine.boolean
with_fitness has a value which is a KBaseRelationEngine.boolean
ref_term_guid has a value which is a string
ref_term_name has a value which is a string
target_term_guid has a value which is a string
target_term_name has a value which is a string


=end text

=back



=head2 GetWSFeatureTermPairsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ws_genome_guid has a value which is a string
target_keapp_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ws_genome_guid has a value which is a string
target_keapp_guid has a value which is a string


=end text

=back



=head2 FeatureTerms

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
feature_guid has a value which is a string
term_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
feature_guid has a value which is a string
term_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 GetFeatureTermsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
taxon_guid has a value which is a string
term_space has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
taxon_guid has a value which is a string
term_space has a value which is a string


=end text

=back



=head2 Term

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
space has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
space has a value which is a string


=end text

=back



=head2 GetTermsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
term_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
term_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 GetOrthologGroupsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
with_term_enrichmnet_profiles has a value which is a KBaseRelationEngine.boolean
app_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
with_term_enrichmnet_profiles has a value which is a KBaseRelationEngine.boolean
app_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 GetOrthologGroupsOutput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ortholog_group_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ortholog_group_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 GetOrthologTermEnrichmentProfilesOutput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ortholog2profiles has a value which is a reference to a hash where the key is a string and the value is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ortholog2profiles has a value which is a reference to a hash where the key is a string and the value is a reference to a list where each element is a KBaseRelationEngine.TermEnrichmentProfile


=end text

=back



=head2 GetOrthologTermEnrichmentProfilesParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ortholog_group_guids has a value which is a reference to a list where each element is a string
app_guids has a value which is a reference to a list where each element is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ortholog_group_guids has a value which is a reference to a list where each element is a string
app_guids has a value which is a reference to a list where each element is a string


=end text

=back



=head2 ws_genome_obj_ref

=over 4



=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 ws_feature_guid

=over 4



=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 ref_ontology_term_guid

=over 4



=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 StoreWSGenomeParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
feature_guids has a value which is a reference to a list where each element is a KBaseRelationEngine.ws_feature_guid

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
feature_guids has a value which is a reference to a list where each element is a KBaseRelationEngine.ws_feature_guid


=end text

=back



=head2 WSFeature

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
function has a value which is a string
aliases has a value which is a reference to a list where each element is a string
ref_term_guid has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
guid has a value which is a string
name has a value which is a string
function has a value which is a string
aliases has a value which is a reference to a list where each element is a string
ref_term_guid has a value which is a string


=end text

=back



=head2 StoreRichWSGenomeParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
features has a value which is a reference to a list where each element is a KBaseRelationEngine.WSFeature
ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
genome_ref has a value which is a KBaseRelationEngine.ws_genome_obj_ref
features has a value which is a reference to a list where each element is a KBaseRelationEngine.WSFeature
ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string


=end text

=back



=head2 ConnectWSFeatures2RefOrthologsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ws2ref_feature_guids has a value which is a reference to a hash where the key is a string and the value is a string


=end text

=back



=head2 ConnectWSFeatures2RefOTermsParams

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
feature2term_list has a value which is a reference to a hash where the key is a KBaseRelationEngine.ws_feature_guid and the value is a reference to a list where each element is a KBaseRelationEngine.ref_ontology_term_guid

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
feature2term_list has a value which is a reference to a hash where the key is a KBaseRelationEngine.ws_feature_guid and the value is a reference to a list where each element is a KBaseRelationEngine.ref_ontology_term_guid


=end text

=back



=cut

package KBaseRelationEngine::KBaseRelationEngineClient::RpcClient;
use base 'JSON::RPC::Client';
use POSIX;
use strict;

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $headers, $obj) = @_;
    my $result;


    {
	if ($uri =~ /\?/) {
	    $result = $self->_get($uri);
	}
	else {
	    Carp::croak "not hashref." unless (ref $obj eq 'HASH');
	    $result = $self->_post($uri, $headers, $obj);
	}

    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $headers, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	@$headers,
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
